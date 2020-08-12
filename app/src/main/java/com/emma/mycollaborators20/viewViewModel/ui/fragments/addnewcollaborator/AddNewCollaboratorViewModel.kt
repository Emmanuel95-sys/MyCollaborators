package com.emma.mycollaborators20.viewViewModel.ui.fragments.addnewcollaborator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom
import kotlinx.coroutines.*

class AddNewCollaboratorViewModel(val database: CollaboratorDatabaseDao,
application: Application) : AndroidViewModel(application) {
    //create a job allows to cancel all co-routines created by this view model
    private var viewModelJob = Job()
    //web service instance

    //when a viewModel is destroyed onCleared is called
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    //Define the scope of the co-routines
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //reference to current collaborator?
    //private var collaboratorFromDB = database.getCollaborator(15)

    //reference to collaborators from room
    //var collaboratorsFromDb = database.getAllCollaborators()
    //function to call when pressing addCollaborators
    fun addCollaborator(name: String?, mail : String?, lat:String, log:String ){
        uiScope.launch {
            val newCollaborator = CollaboratorRoom()

            if(name != "" && name != null){
                newCollaborator.name = name
            }
            if(mail != ""&& mail != null){
                newCollaborator.mail = mail
            }
            newCollaborator.lat = lat
            newCollaborator.log = log
            //get values from edit text
            insert(newCollaborator)
        }
    }

    private suspend fun insert(newCollaborator : CollaboratorRoom){
        withContext(Dispatchers.IO){
            database.insert(newCollaborator)
            var collaboratorsFromDb = database.getAllCollaborators()
            Log.i("clearROOM", collaboratorsFromDb.toString())
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
            var collaboratorsFromDb = database.getAllCollaborators()
            Log.i("clearROOM", collaboratorsFromDb.toString())
        }
    }

}

