package com.emma.mycollaborators20.view.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.ChooserFragmentBinding


class ChooserFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: ChooserFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.chooser_fragment, container, false)

        binding.MyCollaboratorsButton.setOnClickListener {
            findNavController().navigate(R.id.action_chooserFragment_to_collaboratorListFragment)
        }

        binding.AddCollaboratorButton.setOnClickListener {
            findNavController().navigate(R.id.action_chooserFragment_to_addNewCollaboratorFragment)
        }

        return binding.root
    }


}
