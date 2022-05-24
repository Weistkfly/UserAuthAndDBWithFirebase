package com.example.usermanagementfirebase.ui_user_management.user_profile

sealed class UserProfileEvent{
    object SignOut: UserProfileEvent()
    object GetCurrentUser: UserProfileEvent()
}
