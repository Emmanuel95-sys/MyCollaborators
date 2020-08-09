package com.emma.mycollaborators20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabaseDao

class CollaboratorListViewModel (val database : CollaboratorDatabaseDao,
application: Application): AndroidViewModel(application) {



}
