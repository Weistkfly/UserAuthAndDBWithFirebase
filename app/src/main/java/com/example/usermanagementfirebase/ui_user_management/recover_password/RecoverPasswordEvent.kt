package com.example.usermanagementfirebase.ui_user_management.recover_password

sealed class RecoverPasswordEvent{
    data class ResetPassword(val email: String): RecoverPasswordEvent()
}
