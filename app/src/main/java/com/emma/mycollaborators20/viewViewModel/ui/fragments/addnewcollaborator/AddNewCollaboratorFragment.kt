package com.emma.mycollaborators20.viewViewModel.ui.fragments.addnewcollaborator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.AddNewCollaboratorFragmentBinding
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabase


class AddNewCollaboratorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding : AddNewCollaboratorFragmentBinding = DataBindingUtil.inflate(inflater,
        R.layout.add_new_collaborator_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CollaboratorDatabase.getInstance(application).collaboratorDatabaseDao
        
        val viewModelFactory =
            AddNewCollaboratorViewModelFactory(
                dataSource,
                application
            )

        val addCollaboratorListViewModel = ViewModelProvider(this,
            viewModelFactory).get(AddNewCollaboratorViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.AddNewButton.setOnClickListener {
            //capture the strings
            var name = binding.addNewNameET.text.toString()
            var mail = binding.addNewMailET.text.toString()
            var lat = binding.addNewLatET.text.toString()
            var log = binding.addNewLogET.text.toString()
            //here we can use validations using regex
            if(lat == "" || log ==""){
                lat = "19.${(0..30).random()}52213"
                log = "-99.${(0..30).random()}32828"
            }
            //call the view model method
            addCollaboratorListViewModel.addCollaborator(name, mail, lat ,log)
            findNavController().navigate(R.id.action_addNewCollaboratorFragment_to_addedCollaboratorFragment)
        }
        binding.addNewCollaboratorViewModelFromLayout = addCollaboratorListViewModel

        return binding.root
    }

}
