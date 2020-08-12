package com.emma.mycollaborators20.viewViewModel.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.emma.mycollaborators20.R;
import com.emma.mycollaborators20.model.Collaborator;
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom;
import com.emma.mycollaborators20.utils.ReadJson;
import com.emma.mycollaborators20.utils.UnzipUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.emma.mycollaborators20.utils.QueryUtils.extractCollaboratorsFromJsonString;

public class DownloadFileActivity extends AppCompatActivity {

    Button actionButton;
    TextView JsonStringTv;


    private ProgressDialog mProgressDialog;


    String unzipLocation = Environment.getExternalStorageDirectory() + "/testunzip/";
    //here we can modify the MIME type to a .jpg for instance
    String zipFile =Environment.getExternalStorageDirectory() + "/test.zip";
    //
    String unzipFileName = "employees_data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

        actionButton = findViewById(R.id.downloadButton);
        JsonStringTv = findViewById(R.id.tvJsonString);

        checkPermissions();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });

    }

    private void readFile() {
        ReadJson readJson = new ReadJson(unzipLocation, unzipFileName);
        String response = readJson.read_json();
    }

    private void checkPermissions() {
        if(Build.VERSION.SDK_INT>22){
            requestPermissions(new String[] {"android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"
            }, 1);
        }
    }

    private void downloadFile() {
        if(ContextCompat.
                checkSelfPermission
                        (this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            DownloadMapAsync downloadMapAsync = new DownloadMapAsync();
            //change the url to the file you want to download
            downloadMapAsync.execute("https://firebasestorage.googleapis.com/v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97");
        }else{
            Toast.makeText(this, "Please enable the permissions", Toast.LENGTH_LONG).show();
            checkPermissions();
        }
    }


    class DownloadMapAsync extends AsyncTask<String, String, String> {
        String result ="";
        @Override
        protected void onPreExecute() {
            Log.i("DOWNLOAD", "onPreexecute");
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(DownloadFileActivity.this);
            mProgressDialog.setMessage("Downloading Zip File..");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {
                Log.i("DOWNLOAD", "downloading?");

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());

                OutputStream output = new FileOutputStream(zipFile);

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.close();
                input.close();
                result = "true";

            } catch (Exception e) {
                Log.i("DOWNLOAD", e.toString());
                result = "false";
            }
            return null;

        }
        protected void onProgressUpdate(String... progress) {
            Log.i("DOWNLOAD",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            mProgressDialog.dismiss();
            Log.i("DOWNLOAD", result);
            if(result.equalsIgnoreCase("true")){
                try {
                    Log.i("ANDRO_ASYNC", "on postExecute");
                    unzip();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{

            }
        }
    }



    public void unzip() throws IOException {
        Log.i("UNZIP", "creatingProgressDialog");
        mProgressDialog = new ProgressDialog(DownloadFileActivity.this);
        mProgressDialog.setMessage("Please Wait...Extracting zip file ... ");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Log.i("UNZIP METHOD", mProgressDialog.toString());

        new UnZipTask().execute(zipFile,unzipLocation);
    }




    private class UnZipTask extends AsyncTask<String, Void, Boolean> {
        @SuppressWarnings("rawtypes")
        @Override
        protected Boolean doInBackground(String... params) {
            Log.i("UnZipTask", "starting unzipping");
            String filePath = params[0];
            String destinationPath = params[1];
            //creating a new file
            File archive = new File(filePath);
            try {

                Log.i("UnZipTask", "starting inside try block");
                //creating zip file from input stream
                ZipFile zipfile = new ZipFile(archive);

                for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    unzipEntry(zipfile, entry, destinationPath);
                }


                UnzipUtil unzipUtil = new UnzipUtil(zipFile, unzipLocation);
                unzipUtil.unzip();

            } catch (Exception e) {
                Log.i("UnZipTask", e.toString());
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mProgressDialog.dismiss();
            //readFile();
            actionButton.setText("Read File");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readFileWithAsyncTask();
                }
            });
        }


        private void unzipEntry(ZipFile zipfile, ZipEntry entry,
                                String outputDir) throws IOException {

            if (entry.isDirectory()) {
                createDir(new File(outputDir, entry.getName()));
                return;
            }

            File outputFile = new File(outputDir, entry.getName());
            if (!outputFile.getParentFile().exists()) {
                createDir(outputFile.getParentFile());
            }

            Log.i("", "Extracting: " + entry);
            BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

            try {

            } finally {
                outputStream.flush();
                outputStream.close();
                inputStream.close();

            }
        }

        private void createDir(File dir) {
            if (dir.exists()) {
                return;
            }
            if (!dir.mkdirs()) {
                throw new RuntimeException("Can not create dir " + dir);
            }
        }}

    private void readFileWithAsyncTask() {
        Log.i("read file method",  "Start asynctask");

        new ReadFileTask().execute(unzipLocation,unzipFileName);

    }

    private class ReadFileTask extends  AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String filePath = params[0];
            String jsonFileName = params[1];
            ReadJson readJson = new ReadJson(filePath, jsonFileName);
            String response = readJson.read_json();
            return response;
        }

        @Override
        protected void onPostExecute(final String response) {
            JsonStringTv.setText(response);
            //persist data
            actionButton.setText("Persist data");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    persistData(response);
                }
            });

        }
    }

    private void persistData(String response) {
        //send to fragment
        //create a list of object from json response
        List<Collaborator> collaborators = extractCollaboratorsFromJsonString(response);
        //persist the data in local database
        persistDataInRoom(collaborators);

        //draw the items in a rv
    }

    private void persistDataInRoom(List<Collaborator> collaborators) {
        //new PersistDataInRoomAsync().execute(collaborators);
    }


    private class PersistDataInRoomAsync extends AsyncTask< List<Collaborator>, Void , List<CollaboratorRoom>> {

        @Override
        protected List<CollaboratorRoom> doInBackground(List<Collaborator>... lists) {
            List collaboratorsJSONFormat = lists[0];


            return null;
        }

        @Override
        protected void onPostExecute(List<CollaboratorRoom> collaboratorRooms) {
            super.onPostExecute(collaboratorRooms);

        }
    }


}



