package com.example.usermanagementfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usermanagementfirebase.ui.theme.UserManagementFirebaseTheme
import com.example.usermanagementfirebase.ui_user_management.login.LoginScreen
import com.example.usermanagementfirebase.ui_user_management.recover_password.RecoverPasswordScreen
import com.example.usermanagementfirebase.ui_user_management.sign_up.SignUpScreen
import com.example.usermanagementfirebase.ui_user_management.user_profile.UserProfileScreen
import com.example.usermanagementfirebase.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserManagementFirebaseTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.LOGIN_SCREEN
                    ) {
                        composable(Routes.LOGIN_SCREEN){
                            LoginScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(Routes.RECOVER_PASSWORD){
                            RecoverPasswordScreen()
                        }
                        composable(Routes.SIGN_UP){
                            SignUpScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Routes.USER_PROFILE){
                            UserProfileScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


