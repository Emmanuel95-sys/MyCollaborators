package com.emma.mycollaborators20.viewViewModel.ui.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emma.mycollaborators20.R
import com.emma.mycollaborators20.databinding.ChooserFragmentBinding
import com.emma.mycollaborators20.viewViewModel.ui.fragments.Login.LoginFragment
import com.emma.mycollaborators20.viewViewModel.ui.fragments.Login.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


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
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer {authenticationState ->
            when(authenticationState){
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    //log out button
                    //navigation to sig in fragment
                    binding.LogOutButton.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                       //it will automatically navigate
                    }

                }
                else ->{
                    findNavController().navigate(R.id.action_chooserFragment_to_loginFragment)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SIGN_IN_RESULT_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                Log.i(
                    LoginFragment.TAG, "Inicio de sesion exitoso usuario: " +
                        "${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
            }else{
                Log.i(LoginFragment.TAG,"Error con el incio de sesion ${response?.error?.errorCode}")
            }
        }
    }

//    private fun launchSignInFlow(){
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build()
//        )
//        startActivityForResult(
//            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
//                providers
//            ).build(), ChooserFragment.SIGN_IN_RESULT_CODE
//        )
//    }
}
