package com.emma.mycollaborators20.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.CollaboratorListFragmentBinding
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabase
import com.emma.mycollaborators20.viewmodel.CollaboratorListViewModel
import com.emma.mycollaborators20.viewmodel.CollaboratorListViewModelFactory


class CollaboratorListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //put all the dependencies wrap ALL layouts in a layout tag
        val binding: CollaboratorListFragmentBinding  = DataBindingUtil.inflate(inflater,
          R.layout.collaborator_list_fragment, container, false)
        //get reference to the app
        val application = requireNotNull(this.activity).application
        //reference to the data source
        val dataSource = CollaboratorDatabase.getInstance(application).collaboratorDatabaseDao
        //view model factory reference
        val viewModelFactory = CollaboratorListViewModelFactory(dataSource, application)
        //create a view model // CHECK IF THIS IS WORKING!!!!!!
        val collaboratorListViewModel = ViewModelProvider(this,
            viewModelFactory).get(CollaboratorListViewModel::class.java)
        //specify lifecycle owner
        binding.setLifecycleOwner(this)
        //view model layout variable
        binding.collaboratorListViewModelFromLayout = collaboratorListViewModel

        
        return binding.root
    }


}
