package com.example.edistynytmobiili

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.ui.theme.EdistynytMobiiliTheme
import com.example.edistynytmobiili.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdistynytMobiiliTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen(){
    //viewModel huolehtii siitä, että tiedot säilytetään konfiguraatiomuutoksien yhteydessä
    val loginVm: LoginViewModel = viewModel()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {//Tekstikentän valueksi saadaan LoginViewModelissa määritellyn loginStaten arvo,
        // joka rakennetaan Login data classin avulla
        OutlinedTextField(value = loginVm.loginState.value.username, onValueChange = { newUsername ->
            //View modelista kutsutaan funktiota muuttamaan usernamea
            loginVm.setUsername(newUsername)
        }, placeholder = {
            Text(text="Username")
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = loginVm.loginState.value.password,
            onValueChange = { newPassword ->
                loginVm.setPassword(newPassword)
        }, placeholder = {
            Text(text="Password")
        }, visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}
