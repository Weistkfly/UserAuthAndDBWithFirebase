package com.example.usermanagementfirebase.ui_user_management.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermanagementfirebase.UiEvent
import com.example.usermanagementfirebase.data.BaseAuthRepository
import com.example.usermanagementfirebase.util.Routes
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: BaseAuthRepository
) : ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SignInUser -> {
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
                                    repository.signInWithEmailPassword(event.email, event.password)
                                user?.let {
                                    _firebaseUser.postValue(it)
                                }
                                sendUiEvent(UiEvent.Navigate(Routes.USER_PROFILE))
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
            is LoginEvent.NavigateToResetPassword -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.RECOVER_PASSWORD))
                }
            }
            is LoginEvent.NavigateToSignUp -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.SIGN_UP))
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