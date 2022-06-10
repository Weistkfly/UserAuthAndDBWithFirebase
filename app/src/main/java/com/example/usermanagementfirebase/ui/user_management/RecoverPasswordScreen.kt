package com.example.usermanagementfirebase.ui.user_management.recover_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usermanagementfirebase.UiEvent
import com.example.usermanagementfirebase.ui.user_management.MainViewModel

@Composable
fun RecoverPasswordScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Reset your password")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "JohnDoe@mail.com") }
            )
            Button(
                onClick = { viewModel.onEvent(RecoverPasswordEvent.ResetPassword(email.text))
                }) {
                Text(text = "Reset password")
            }
        }
    }
}