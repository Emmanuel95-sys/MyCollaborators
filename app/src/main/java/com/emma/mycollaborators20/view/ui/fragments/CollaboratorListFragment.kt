package com.emma.mycollaborators20.view.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.viewmodel.CollaboratorListViewModel

class CollaboratorListFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //val binding: FragmentCollaboratorList = DataBindingUtil.inflate(inflater,
       // R.layout.collaborator_list_fragment, container, false)
        return inflater.inflate(R.layout.collaborator_list_fragment, container, false)
    }


}
