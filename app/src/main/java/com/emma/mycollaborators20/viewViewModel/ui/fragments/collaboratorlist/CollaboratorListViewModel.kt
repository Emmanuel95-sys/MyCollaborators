package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist

import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom
import com.emma.mycollaborators20.model.restapi.ServiceInterface.FileApiService
import com.emma.mycollaborators20.utils.QueryUtilsKt
import com.emma.mycollaborators20.utils.ReadJsonKt
import com.emma.mycollaborators20.utils.UnzipUtilKt
import com.emma.mycollaborators20.utils.Utils
import kotlinx.coroutines.*


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

    //web service instance
    private val webService = FileApiService()

    fun callWebService() {
        uiScope.launch {
            val response = webService.getResponseAsync().await()
            val fileURl = response.data.file
            Log.i("CollabViewModel", fileURl)
            val utils = Utils()
            utils.downloadFile(fileURl)
            unZipFileVM()
        }
    }

    private suspend fun unZipFileVM(){
        val unZipper = UnzipUtilKt()
        unZipper.UnzipUtil(zipFile, unzipFileLocation)
        val fileName =   unZipper.unZip()
        readFile(fileName)
    }

    private suspend fun readFile(fileName: String?){
        val fileReader = ReadJsonKt()
        fileReader.ReadJsonKt(unzipFileLocation, fileName)
        val jsonString = fileReader.readJson()
        createListFromJsonString(jsonString)
    }

    private suspend fun createListFromJsonString(jsonString : String?){
        val queryUtils = QueryUtilsKt()
        val collaboratorsFromWS =
            queryUtils.extractCollaboratorsFromJsonString(jsonString)
        Log.i("VIEWMODEL" , collaboratorsFromWS.toString())
        if (collaboratorsFromWS != null) {
            for (collaboratorWS in collaboratorsFromWS){
                val newCollaborator = CollaboratorRoom()
                newCollaborator.name  = collaboratorWS.name
                newCollaborator.jsonId = collaboratorWS.id
                newCollaborator.mail = collaboratorWS.mail

                val locationWs = collaboratorWS.location
                val lat = locationWs.lat
                val log = locationWs.log

                newCollaborator.log = log
                newCollaborator.lat = lat
                insert(newCollaborator)
            }
        }

    }

    private suspend fun insert(newCollaborator : CollaboratorRoom){
        withContext(Dispatchers.IO){
            database.insert(newCollaborator)
        }
    }
    //add collaborators from web service



}
