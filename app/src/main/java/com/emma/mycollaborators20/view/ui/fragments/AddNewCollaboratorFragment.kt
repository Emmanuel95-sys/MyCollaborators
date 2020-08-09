package com.emma.mycollaborators20.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.AddNewCollaboratorFragmentBinding
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabase
import com.emma.mycollaborators20.viewmodel.AddNewCollaboratorViewModel
import com.emma.mycollaborators20.viewmodel.AddNewCollaboratorViewModelFactory


class AddNewCollaboratorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding : AddNewCollaboratorFragmentBinding = DataBindingUtil.inflate(inflater,
        R.layout.add_new_collaborator_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CollaboratorDatabase.getInstance(application).collaboratorDatabaseDao
        
        val viewModelFactory = AddNewCollaboratorViewModelFactory(dataSource, application)

        val addCollaboratorListViewModel = ViewModelProvider(this,
            viewModelFactory).get(AddNewCollaboratorViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.addNewCollaboratorViewModelFromLayout = addCollaboratorListViewModel

        return binding.root
    }

}
