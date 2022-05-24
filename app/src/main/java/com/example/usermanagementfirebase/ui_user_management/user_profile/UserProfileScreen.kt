package com.example.usermanagementfirebase.ui_user_management.user_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usermanagementfirebase.UiEvent

@Composable
fun UserProfileScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val userEmail = viewModel.user?.email
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "User: $userEmail")
            Button(onClick = {
                viewModel.onEvent(UserProfileEvent.SignOut)
            }) {
                Text(text = "Sign out")
            }
        }
    }
}