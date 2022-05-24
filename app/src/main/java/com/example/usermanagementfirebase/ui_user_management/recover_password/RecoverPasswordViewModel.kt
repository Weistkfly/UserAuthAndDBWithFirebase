package com.example.usermanagementfirebase.ui_user_management.recover_password

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
class RecoverPasswordViewModel @Inject constructor(
    val repository: BaseAuthRepository
) : ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = _firebaseUser

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RecoverPasswordEvent) {
        when (event) {
            is RecoverPasswordEvent.ResetPassword -> {
                viewModelScope.launch {
                    if (event.email.isEmpty()) {
                        try {
                            val result = repository.sendResetPassword(event.email)
                            if (result) {
                                sendUiEvent(
                                    UiEvent.ShowSnackbar(
                                        message = "Reset email sent"
                                    )
                                )
                            } else {
                                sendUiEvent(
                                    UiEvent.ShowSnackbar(
                                        message = "Could not sent password reset"
                                    )
                                )
                            }
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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}