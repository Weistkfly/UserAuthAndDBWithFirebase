package com.example.usermanagementfirebase.ui.user_management.user_profile

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usermanagementfirebase.UiEvent
import com.example.usermanagementfirebase.ui.user_management.MainViewModel

@Composable
fun UserProfileScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "User: $userEmail")
            Text(text = "Name: ${viewModel.userInfo?.name} ${viewModel.userInfo?.lastName}}")
            Text(text = "Phone number: ${viewModel.userInfo?.phoneNumber} ")
            Text(text = "Date of birth: ${viewModel.userInfo?.birthDate}")
            Text(text = "Sex: ${viewModel.userInfo?.sex}")
            Text(text = "Country: ${viewModel.userInfo?.country} ")
            Text(text = "State: ${viewModel.userInfo?.state}")
            Text(text = "Address: ${viewModel.userInfo?.address}")
            Button(onClick = {
                viewModel.onEvent(UserProfileEvent.GetCurrentUser)
            }) {
                Text(text = "Sign out")
            }
        }
    }
}