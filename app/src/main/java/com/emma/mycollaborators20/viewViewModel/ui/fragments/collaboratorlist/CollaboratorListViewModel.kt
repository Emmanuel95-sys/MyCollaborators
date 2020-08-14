package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist

import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import com.emma.mycollaborators20.model.restapi.ServiceInterface.FileApiService
import com.emma.mycollaborators20.utils.Utils
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL

//check if this works
 var zipFile: String = Environment.getExternalStorageDirectory().toString() + "/test.zip"
var unzipFileLocation = Environment.getExternalStorageDirectory().toString() + "/testunzip/"

class CollaboratorListViewModel (
    private val database : CollaboratorDatabaseDao,
    application: Application): AndroidViewModel(application) {
    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var collaborators = database.getAllCollaborators()
    //var response string
    var responseString : String = ""
    //web service instance
    private val webService = FileApiService()

    fun initializeWebFileSequence(){

    }

    fun callWebService() {
        uiScope.launch {
            val response = webService.getResponse().await()
            val fileURl = response.data.file
            Log.i("CollabViewModel", fileURl)
            var utils = Utils()
            utils.downloadFile(fileURl)
        }
    }




}
