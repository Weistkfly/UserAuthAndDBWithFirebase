package com.example.usermanagementfirebase.ui_user_management.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermanagementfirebase.UiEvent
import com.example.usermanagementfirebase.data.BaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val repository: BaseAuthRepository
) : ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    //val currentUser get() = _firebaseUser

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUpUser -> {
                viewModelScope.launch {
                    when {
                        event.email.isEmpty() -> {
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = "Email cannot be empty"
                                )
                            )
                        }
                        event.password.isEmpty() -> {
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = "Password cannot be empty"
                                )
                            )
                        }
                        else -> {
                            try {
                                val user =
                                    repository.signUpWithEmailPassword(event.email, event.password)
                                user?.let {
                                    _firebaseUser.postValue(it)
                                    sendUiEvent(
                                        UiEvent.ShowSnackbar(
                                            message = "Sign up successful"
                                        )
                                    )
                                }
                                sendUiEvent(UiEvent.PopBackStack)
                            } catch (e: Exception) {
                                sendUiEvent(
                                    UiEvent.ShowSnackbar(
                                        message = "Something went wrong"
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}