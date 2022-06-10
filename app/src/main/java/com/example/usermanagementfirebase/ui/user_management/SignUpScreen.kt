package com.example.usermanagementfirebase.ui.user_management.sign_up


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.usermanagementfirebase.R
import com.example.usermanagementfirebase.UiEvent
import com.example.usermanagementfirebase.ui.user_management.MainViewModel

@Composable
fun SignUpScreen(
    onPopBackStack: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var lastname by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var sex by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var province by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var country by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    var passwordVisibility by remember { mutableStateOf(false) }

    val countries = stringArrayResource(R.array.countries)
    val focusManager = LocalFocusManager.current

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign up",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "JohnDoe@mail.com") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "*********") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        }
                    ) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(id = R.drawable.ic_visibility) else painterResource(id = R.drawable.ic_visibility_off),
                            contentDescription = "xd"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                placeholder = { Text(text = "John") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = lastname,
                onValueChange = { lastname = it },
                label = { Text(text = "Last name") },
                placeholder = { Text(text = "Doe") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(text = "Phone number") },
                placeholder = { Text(text = "xxx-xxx-xxxx") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            Text(text = "Sex")
            Row(
                Modifier.selectableGroup(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sex,
                    onClick = { sex = true
                    }
                )
                Text(text = "Masculine")
                RadioButton(
                    selected = !sex,
                    onClick = { sex = false
                    }
                )
                Text(text = "Feminine")
            }
            val birthDate = showDatePicker(context = context)
            val icon = if (expanded)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Country") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                countries.forEach { label ->
                    DropdownMenuItem(onClick = {
                        country = label
                        expanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
            OutlinedTextField(
                value = province,
                onValueChange = { province = it },
                label = { Text(text = "State") },
                placeholder = { Text(text = "New york") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(text = "Address") },
                placeholder = { Text(text = "Junco Vel, #11") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.onEvent(SignUpEvent.SignUpUser(email.text, password.text))
                        viewModel.onEvent(SignUpEvent.RegisterUserInfo(name.text, lastname.text, phoneNumber.text, sex, birthDate, country, province.text, address.text))
                    }
                )
            )
            Button(
                onClick = {
                    viewModel.onEvent(SignUpEvent.SignUpUser(email.text, password.text))
                    viewModel.onEvent(SignUpEvent.RegisterUserInfo(name.text, lastname.text, phoneNumber.text, sex, birthDate, country, province.text, address.text))
                }) {
                Text(text = "Sign up")
            }
            TextButton(
                onClick = {
                    viewModel.onEvent(SignUpEvent.NavigateToLogin)
                }) {
                Text(
                    text = "Already have an account? Login",
                    fontSize = 10.sp
                )
            }
        }
    }
}

