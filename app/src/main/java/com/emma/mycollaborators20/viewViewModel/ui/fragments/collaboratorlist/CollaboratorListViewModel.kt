package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import kotlinx.coroutines.*

class CollaboratorListViewModel (val database : CollaboratorDatabaseDao,
application: Application): AndroidViewModel(application) {
    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var collaborators = database.getAllCollaborators()
//    fun retrieveCollaborators() {
//        uiScope.launch {
//            retrieveCollaboratorsFromRoom()
//        }
//    }

//    private suspend fun retrieveCollaboratorsFromRoom() {
//        withContext(Dispatchers.IO){
//            database.getAllCollaborators()
//        }
//    }
}
