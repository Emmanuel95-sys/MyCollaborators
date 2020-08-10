package com.emma.mycollaborators20.viewmodel

import androidx.lifecycle.map
import androidx.lifecycle.ViewModel
import com.emma.mycollaborators20.viewmodel.LiveData.FirebaseUserLiveData

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
