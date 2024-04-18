package com.example.edistynytmobiili.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.DirectingText
import com.example.edistynytmobiili.components.PasswordTextField
import com.example.edistynytmobiili.components.UsernameTextField
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
@Composable
fun RegistrationScreen(goToLogin : () -> Unit) {
    val vm: RegistrationViewModel = viewModel()
    val context = LocalContext.current

    //LaunchedEffect errorin toastiin tulostamiselle, tällä tulostetaan muut kuin username/password errorit
    LaunchedEffect(key1 = vm.registrationState.value.errorMsg) {
        if (!vm.registrationState.value.isUsernameError && !vm.registrationState.value.isPasswordError)
        {
            vm.registrationState.value.errorMsg?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        /*
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ){
            Text("Create a new account", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
         */
        when {
            vm.registrationState.value.loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            vm.registrationState.value.status ->
                RegistrationConfirmation(
                    username = vm.registrationState.value.username,
                    onContinue = { goToLogin() }
                )
            else -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                UsernameTextField(
                    username = vm.registrationState.value.username,
                    onUsernameChange = { newValue ->
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