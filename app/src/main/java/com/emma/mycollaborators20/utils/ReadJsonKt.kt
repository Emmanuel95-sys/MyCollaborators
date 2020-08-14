package com.emma.mycollaborators20.utils

import android.util.Log
import java.io.*

class ReadJsonKt {
    private val TAG = "ReadJsonClass"

    private var _filepath: String? = null
    private var _fileName: String? = null

    fun ReadJsonKt(filepath: String?, fileName: String?) {
        _fileName = fileName
        _filepath = filepath
    }

    fun readJson(): String?  {
        val file = File(_filepath, _fileName)
        var fileReader: FileReader? = null
        try {
            fileReader = FileReader(file)
        } catch (e: FileNotFoundException) {
            Log.i(TAG, e.toString())
            e.printStackTrace()
        }
        val bufferedReader = BufferedReader(fileReader)
        val outputString = StringBuilder()
        var line: String? = null
        try {
            line = bufferedReader.readLine()
        } catch (e: IOException) {
            Log.i(TAG, e.toString())
            e.printStackTrace()
        }
        while (line != null) {
            outputString.append(line)
            try {
                line = bufferedReader.readLine()
            } catch (e: IOException) {
                Log.i(TAG, e.toString())
                e.printStackTrace()
            }
        }
        try {
            bufferedReader.close()
        } catch (e: IOException) {
            Log.i(TAG, e.toString())
            e.printStackTrace()
        }
        // This responce will have Json Format String
        return outputString.toString()
    }

}
