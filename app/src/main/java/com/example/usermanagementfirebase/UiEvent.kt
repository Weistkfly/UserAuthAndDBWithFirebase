package com.example.usermanagementfirebase

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class ShowSnackbar(val message: String): UiEvent()
    data class Navigate(val route: String): UiEvent()
}
