package com.emma.mycollaborators20.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom
import kotlinx.coroutines.*

class AddNewCollaboratorViewModel(val database: CollaboratorDatabaseDao,
application: Application) : ViewModel() {
    //create a job allows to cancel all co-routines created by this view model
    private var viewModeJob = Job()
    //web service instance

    //when a viewModel is destroyed onCleared is called
    override fun onCleared() {
        super.onCleared()
        viewModeJob.cancel()
    }
    //Define the scope of the co-routines
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)
    //reference to current collaborator?
    //private var collaboratorFromDB = database.getCollaborator(15)
    //reference to collaborators from room
    private var collaboratorsFromDb = database.getAllCollaborators()

    //function to call when pressing addCollaborators
    fun addCollaborator(){
        uiScope.launch {
            val newCollaborator = CollaboratorRoom()
            //get values from edit text
            insert(newCollaborator)

        }
    }

    private suspend fun insert(newCollaborator : CollaboratorRoom){
        withContext(Dispatchers.IO){
            database.insert(newCollaborator)
            var collaboratorFromDB = database.getCollaborator(15)
            var collaboratorsFromDb = database.getAllCollaborators()
            Log.i("CollaboratorsROOM", collaboratorFromDB.toString())
            Log.i("CollaboratorsROOM", collaboratorsFromDb.value.toString())
        }
    }

    //clean information
    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

}

