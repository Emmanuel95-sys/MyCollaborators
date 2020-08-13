package com.emma.mycollaborators20.utils

import android.util.Log
import com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist.zipFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL

open class Utils {

     suspend fun  downloadFile(urlString :String){
        withContext(Dispatchers.IO){
            try{
                var count: Int
                val urlObject = URL(urlString)
                val connexion = urlObject.openConnection()
                connexion.connect()

                val inputStream = BufferedInputStream(urlObject.openStream())
                val outputStream = FileOutputStream(zipFile)

                val data = ByteArray(1024)
                var total: Long = 0

                while (inputStream.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    outputStream.write(data, 0, count)
                }
                outputStream.close()
                inputStream.close()

            }catch (e: Exception) {
                var error = e.toString()
                Log.i("Download", error)
            }
        }
    }



}