package com.example.usermanagementfirebase.ui_user_management.login

sealed class LoginEvent {
    data class SignInUser(val email: String, val password: String): LoginEvent()
    object NavigateToSignUp: LoginEvent()
    object NavigateToResetPassword: LoginEvent()

}
