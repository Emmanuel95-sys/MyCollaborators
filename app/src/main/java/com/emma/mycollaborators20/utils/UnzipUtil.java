package com.emma.mycollaborators20.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtil {
    private String _zipFile;
    private String _location;
    private String TAG = "UnzipUtil";

    public UnzipUtil(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }

    public void unzip() {
        try  {
            Log.i(TAG,"initializing unzipping");
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry unzipFile = null;
            while ((unzipFile = zin.getNextEntry()) != null) {
                Log.i(TAG + "Decompress", "Unzipping " + unzipFile.getName());

                if(unzipFile.isDirectory()) {
                    _dirChecker(unzipFile.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + unzipFile.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);


                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = zin.read(buffer)) != -1) {
                            fout.write(buffer, 0, len);
                        }
                        fout.close();

                    }

                    zin.closeEntry();
                    fout.close();
                }
            }
            zin.close();
        } catch(Exception e) {
            Log.i(TAG + "Decompress", "unzip", e);
        }
    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }

}