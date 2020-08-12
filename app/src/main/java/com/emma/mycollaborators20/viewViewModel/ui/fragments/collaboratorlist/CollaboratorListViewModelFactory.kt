package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao
import java.lang.IllegalArgumentException

class CollaboratorListViewModelFactory (
    private val dataSource: CollaboratorDatabaseDao,
    private val application: Application): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CollaboratorListViewModel::class.java)){
            return CollaboratorListViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}