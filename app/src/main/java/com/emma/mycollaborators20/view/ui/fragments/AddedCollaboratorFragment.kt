package com.emma.mycollaborators20.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.FragmentAddedCollaboratorBinding

/**
 * A simple [Fragment] subclass.
 */
class AddedCollaboratorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding :FragmentAddedCollaboratorBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_added_collaborator, container,false)

        binding.AddOtherCollaboratorButton.setOnClickListener {
            findNavController().navigate(R.id.action_addedCollaboratorFragment_to_addNewCollaboratorFragment)
        }
        binding.MainMenuButton.setOnClickListener {
            findNavController().navigate(R.id.action_addedCollaboratorFragment_to_chooserFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}
