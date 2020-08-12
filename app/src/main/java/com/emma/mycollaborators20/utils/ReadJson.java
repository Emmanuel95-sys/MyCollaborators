package com.emma.mycollaborators20.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ReadJson {
    private String TAG = "ReadJsonClass";

    private static String _filepath;
    private String _fileName;

    public ReadJson(String filepath,String fileName){
        _fileName = fileName;
        _filepath = filepath;
    }

    public String read_json()  {
        File file = new File(_filepath, _fileName);

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            Log.i(TAG, e.toString());
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder outputString = new StringBuilder();
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            Log.i(TAG, e.toString());
            e.printStackTrace();
        }
        while (line != null){
            outputString.append(line);
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                Log.i(TAG, e.toString());
                e.printStackTrace();
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            Log.i(TAG, e.toString());
            e.printStackTrace();
        }
        // This responce will have Json Format String
        //Log.i(TAG, outputString.toString());
        return outputString.toString();
    }

}
