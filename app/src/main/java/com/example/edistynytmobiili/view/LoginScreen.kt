package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.components.DirectingText
import com.example.edistynytmobiili.components.PasswordTextField
import com.example.edistynytmobiili.components.UsernameTextField
import com.example.edistynytmobiili.viewmodel.LoginViewModel


@Composable
//Loginscreenin argumentteihin navigointia varten callback-funktio
fun LoginScreen(goToCategories: () -> Unit, goToRegistration: () -> Unit) {

    val vm: LoginViewModel = viewModel()
    val context = LocalContext.current
    //LaunchedEffect errorin toastiin tulostamiselle
    LaunchedEffect(key1 = vm.loginState.value.errorMsg) {
        vm.loginState.value.errorMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    //LaunchedEffect onnistuneen kirjautumisen johdosta poisnavigoinnille
    LaunchedEffect(key1 = vm.loginState.value.status) {
        if (vm.loginState.value.status) {
            goToCategories()
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
            Text("Log in", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }

        */
        when {
            vm.loginState.value.loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            else -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                UsernameTextField(
                    username = vm.loginState.value.username,
                    onUsernameChange = { newUsername ->
                        vm.setUsername(newUsername)
                    },
                )
                PasswordTextField(
                    password = vm.loginState.value.password,
                    onPasswordChange = { newValue ->
                        vm.setPassword(newValue)
                    }
                )

                Button(
                    enabled = vm.loginState.value.username != "" && vm.loginState.value.password != "",
                    onClick = { vm.login() },
                    modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(12.dp))
                DirectingText(
                    directingText = "New user?",
                    textButtonText = "Sign Up",
                    onClick = { goToRegistration() }
                )
            }
        }
    }

}