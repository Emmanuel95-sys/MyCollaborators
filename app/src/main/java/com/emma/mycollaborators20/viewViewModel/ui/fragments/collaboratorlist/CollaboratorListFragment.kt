package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.CollaboratorListFragmentBinding
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabase
import com.emma.mycollaborators20.viewViewModel.adapters.CollaboratorAdapter


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
        val viewModelFactory =
            CollaboratorListViewModelFactory(
                dataSource,
                application
            )
        //create a view model
        val collaboratorListViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CollaboratorListViewModel::class.java)
        //view model layout variable
        binding.collaboratorListViewModelFromLayout = collaboratorListViewModel

        //make adapter
        val adapter = CollaboratorAdapter()
        binding.collaboratorRV.adapter = adapter
        collaboratorListViewModel.collaborators.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.collaboratorsData = it
        }
        })


        binding.setLifecycleOwner(this)
        return binding.root
    }
}
