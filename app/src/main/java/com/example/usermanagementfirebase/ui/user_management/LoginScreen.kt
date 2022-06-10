package com.example.usermanagementfirebase.ui.user_management

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usermanagementfirebase.UiEvent
import com.example.usermanagementfirebase.ui.user_management.MainViewModel

@Composable
fun LoginScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var userPassword by remember { mutableStateOf(TextFieldValue("")) }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
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

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Login into your account")
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text(text = "Email")},
                placeholder = {Text(text = "JohnDoe@mail.com")}
            )
            OutlinedTextField(
                value = userPassword,
                onValueChange = {userPassword = it},
                label = { Text(text = "Password")},
                placeholder = {Text(text = "*********")},
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = {
                    viewModel.onEvent(LoginEvent.SignInUser(email.text, userPassword.text))
                }) {
                Text(text = "Login")
            }
            TextButton(
                onClick = {
                    viewModel.onEvent(LoginEvent.NavigateToSignUp)
                }) {
                Text(
                    text = "Don't have an account? Sign Up",
                    fontSize = 8.sp
                )
            }
            TextButton(
                onClick = {
                    viewModel.onEvent(LoginEvent.NavigateToResetPassword)
                }) {
                Text(
                    text = "Forgot your password? Reset password",
                    fontSize = 8.sp
                )
            }
    }
    }
}