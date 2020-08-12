package com.emma.mycollaborators20.viewViewModel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.ChooserFragmentBinding
import com.emma.mycollaborators20.viewViewModel.ui.fragments.Login.LoginViewModel
import com.firebase.ui.auth.AuthUI


class ChooserFragment : Fragment() {
    companion object{
        const val TAG = "ChooserFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    //get view model reference
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding : ChooserFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.chooser_fragment, container, false)

        binding.MyCollaboratorsButton.setOnClickListener {
            findNavController().navigate(R.id.action_chooserFragment_to_collaboratorListFragment)
        }

        binding.AddCollaboratorButton.setOnClickListener {
            findNavController().navigate(R.id.action_chooserFragment_to_addNewCollaboratorFragment)
        }
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observeAuthenticationState()
    }

    private fun launchSignInFloe(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                providers
            ).build(), ChooserFragment.SIGN_IN_RESULT_CODE
        )
    }
}
