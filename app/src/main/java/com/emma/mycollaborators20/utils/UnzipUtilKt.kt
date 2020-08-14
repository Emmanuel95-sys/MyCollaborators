package com.emma.mycollaborators20.utils

import android.util.Log
import com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist.zipFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

open class UnzipUtilKt {
    private var _zipFile: String? = null
    private var _location: String? = null
    private var zipFileName : String ? = null
    private val TAG = "UnzipUtil"

    fun UnzipUtil(zipFile: String?, location: String?) {
        _zipFile = zipFile
        _location = location
        _dirChecker("")
    }

    fun unZip(): String? {
        try {
            Log.i(TAG, "initializing unzipping")
            val fin = FileInputStream(_zipFile)
            val zin = ZipInputStream(fin)
            var unzipFile: ZipEntry? = null
            while (zin.nextEntry.also { unzipFile = it } != null) {
                Log.i(TAG + "Decompress", "Unzipping " + unzipFile!!.name)
                if (unzipFile!!.isDirectory) {
                    _dirChecker(unzipFile!!.name)
                } else {
                    val fout =
                        FileOutputStream(_location + unzipFile!!.name)
                    zipFileName = unzipFile!!.name
                    var c = zin.read()
                    while (c != -1) {
                        fout.write(c)
                        val buffer = ByteArray(8192)
                        var len: Int
                        while (zin.read(buffer).also { len = it } != -1) {
                            fout.write(buffer, 0, len)
                        }
                        fout.close()
                        c = zin.read()
                    }
                    zin.closeEntry()
                    fout.close()
                }
            }
            zin.close()
        } catch (e: Exception) {
            Log.i(TAG + "Decompress", "unzip", e)
        }
        return zipFileName
    }

    private fun _dirChecker(dir: String) {
        val f = File(_location + dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }

}