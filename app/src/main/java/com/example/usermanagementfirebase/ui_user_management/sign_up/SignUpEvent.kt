package com.example.usermanagementfirebase.ui_user_management.sign_up

sealed class SignUpEvent{
    data class SignUpUser(val email: String, val password: String): SignUpEvent()
}
