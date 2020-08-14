package com.emma.mycollaborators20.viewViewModel.ui.fragments.Login

import androidx.lifecycle.map
import androidx.lifecycle.ViewModel
import com.emma.mycollaborators20.LiveData.FirebaseUserLiveData

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map{user ->
        if(user != null ){
            AuthenticationState.AUTHENTICATED
        }else{
            AuthenticationState.UNAUTHENTICATED
        }
    }
}
