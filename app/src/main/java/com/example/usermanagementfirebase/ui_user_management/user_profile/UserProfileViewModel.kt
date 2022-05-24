package com.example.usermanagementfirebase.ui_user_management.user_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    val repository: BaseAuthRepository
) : ViewModel() {

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()

    var user by mutableStateOf<FirebaseUser?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            user = repository.getCurrentUser()
        }
    }

    fun onEvent(event: UserProfileEvent) {
        when (event) {
            is UserProfileEvent.GetCurrentUser -> {
                viewModelScope.launch {
                    user = repository.getCurrentUser()
                    _firebaseUser.postValue(user)
                }
            }
            is UserProfileEvent.SignOut -> {
                viewModelScope.launch {
                    try {
                        user = repository.signOut()
                        user?.let {
                            sendUiEvent(
                                UiEvent.ShowSnackbar(
                                    message = "Logout failed"
                                )
                            )
                        } ?: sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Logout successful"
                            )
                        )

                        UserProfileEvent.GetCurrentUser
                        sendUiEvent(UiEvent.Navigate(Routes.LOGIN_SCREEN))

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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}