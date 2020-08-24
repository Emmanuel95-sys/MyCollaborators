package com.emma.mycollaborators20.viewViewModel.ui.fragments.collaboratorlist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.CollaboratorListFragmentBinding
import com.emma.mycollaborators20.model.CollaboratorSerializable
import com.emma.mycollaborators20.model.localdb.CollaboratorDatabase
import com.emma.mycollaborators20.model.localdb.CollaboratorRoom
import com.emma.mycollaborators20.viewViewModel.adapters.CollaboratorAdapter
import com.emma.mycollaborators20.viewViewModel.adapters.ItemClickListener


class CollaboratorListFragment : Fragment(),
    ItemClickListener<CollaboratorSerializable> {

    private lateinit var collaboratorListViewModel: CollaboratorListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //put all the dependencies wrap ALL layouts in a layout tag
        val binding: CollaboratorListFragmentBinding  = DataBindingUtil.inflate(inflater,
          R.layout.collaborator_list_fragment, container, false)
        //get reference to the app
        val application = requireNotNull(this.activity).application
        //reference to the data source
        val dataSource =
            CollaboratorDatabase.getInstance(application).collaboratorDatabaseDao
        //view model factory reference
        val viewModelFactory =
            CollaboratorListViewModelFactory(dataSource, application)
        //create a view model
        collaboratorListViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CollaboratorListViewModel::class.java)
        //view model layout variable
        binding.collaboratorListViewModelFromLayout = collaboratorListViewModel

        //make adapter
        val adapter = CollaboratorAdapter(this)
        binding.collaboratorRV.adapter = adapter
        collaboratorListViewModel.collaborators.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.collaboratorsData = it
            }
        })

        //map list navigation
        binding.collaboratorMap.setOnClickListener {
            findNavController().navigate(
                R.id.action_collaboratorListFragment_to_collaboratorMapListFragment)
        }
        //Set Menu
        setHasOptionsMenu(true)
        //check permissions
        checkPermissions()

        binding.setLifecycleOwner(this)
        return binding.root
    }

    private fun callWebService(){
        if (ContextCompat.checkSelfPermission(requireNotNull(this.activity),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            collaboratorListViewModel.callWebService()
        } else {
            Toast.makeText(this.activity, "Please enable the permissions",
                Toast.LENGTH_LONG).show()
            checkPermissions()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.call_web_service -> callWebService()
        }
        return true
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(arrayOf("android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"), 1
            )
        }
    }

    override fun onClick(listObject: CollaboratorSerializable, position: Int) {
        val clickedItem = bundleOf("collaborator" to listObject)
        findNavController().navigate(
            R.id.action_collaboratorListFragment_to_collaboratorMapDetailFragment,
            clickedItem)
    }

}
