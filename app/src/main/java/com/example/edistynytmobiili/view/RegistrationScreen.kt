package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.DirectingText
import com.example.edistynytmobiili.components.NameTextField
import com.example.edistynytmobiili.components.PasswordTextField
import com.example.edistynytmobiili.viewmodel.RegistrationViewModel

@Composable
fun RegistrationConfirmation(
    username: String,
    onContinue : () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your new username is",
                fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = username,
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Button(onClick = { onContinue() }) {
            Text("Continue to login")
            Icon(Icons.Filled.Login, contentDescription = "Continue to login",
                modifier = Modifier.padding(start = 10.dp))
        }

    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(goToLogin : () -> Unit) {
    val vm: RegistrationViewModel = viewModel()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = {Text("Sign up") },
            navigationIcon = {
                IconButton(onClick = { goToLogin() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back to login"
                    )
                }
            })
        }
    ) {
        //LaunchedEffect errorin toastiin tulostamiselle, tällä tulostetaan muut kuin username/password errorit
        LaunchedEffect(key1 = vm.registrationState.value.errorMsg) {
            if (!vm.registrationState.value.isUsernameError && !vm.registrationState.value.isPasswordError) {
                vm.registrationState.value.errorMsg?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            when {
                vm.registrationState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                vm.registrationState.value.status ->
                    RegistrationConfirmation(
                        username = vm.registrationState.value.username,
                        onContinue = { goToLogin() }
                    )

                else -> Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text("Create your username and password", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(30.dp))
                    NameTextField(
                        name = vm.registrationState.value.username,
                        onNameChange = { newValue ->
                            vm.setUsername(newValue)
                        },
                        isError = vm.registrationState.value.isUsernameError,
                        errorMsg = vm.registrationState.value.errorMsg
                    )
                    PasswordTextField(
                        password = vm.registrationState.value.password,
                        onPasswordChange = { newValue ->
                            vm.setPassword(newValue)
                        }
                    )
                    PasswordTextField(
                        password = vm.registrationState.value.passwordConfirm,
                        onPasswordChange = { newValue ->
                            vm.setPasswordConfirm(newValue)
                        },
                        isError = vm.registrationState.value.isPasswordError,
                        errorMsg = vm.registrationState.value.errorMsg,
                        textFieldLabel = "Confirm password"

                    )
                    Button(
                        enabled = vm.registrationState.value.username != ""
                                && vm.registrationState.value.password != ""
                                && vm.registrationState.value.passwordConfirm != "",
                        onClick = { vm.register() },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = "Register")
                    }
                    DirectingText(
                        directingText = "Already have an account?",
                        textButtonText = "Log in",
                        onClick = { goToLogin() },
                    )
                }
            }
        }
    }
}