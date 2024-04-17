package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.viewmodel.LoginViewModel


@Composable
//Loginscreenin argumentteihin navigointia varten callback-funktio
fun LoginScreen(goToCategories: () -> Unit) {

    val vm: LoginViewModel = viewModel()
    val context = LocalContext.current
    //LaunchedEffect errorin toastiin tulostamiselle
    LaunchedEffect(key1 = vm.loginState.value.errorMsg) {
        vm.loginState.value.errorMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            //vm.clearError()
        }
    }

    //LaunchedEffect onnistuneen kirjautumisen johdosta poisnavigoinnille
    LaunchedEffect(key1 = vm.loginState.value.loginStatus) {
        if (vm.loginState.value.loginStatus) {
            goToCategories()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            //Kun todetaan sovelluksen olevan lataustilassa, näytetään keskellä näyttöä latausikoni
            vm.loginState.value.loading -> CircularProgressIndicator(modifier = Modifier.align(
                Alignment.Center))
            //Muissa tapauksissa näytetään tavallinen sisältö; Username- ja Password-tekstikentät sekä Login-nappi
            else -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {//Tekstikentän valueksi saadaan LoginViewModelissa määritellyn loginStaten arvo,
                // joka rakennetaan Login data classin avulla
                OutlinedTextField(value = vm.loginState.value.username, onValueChange = { newUsername ->
                    //Tekstikentän arvon muuttuessa ViewModelista kutsutaan funktiota muuttamaan usernamea
                    vm.setUsername(newUsername)
                }, placeholder = {
                    Text(text="Username")
                })
                //Spacerillä lisätään haluttu määrä tilaa komponenttien välille
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = vm.loginState.value.password,
                    onValueChange = { newPassword ->
                        vm.setPassword(newPassword)
                    }, placeholder = {
                        Text(text="Password")
                        //visualTransformationilla saadaan salasana sensuroiduksi.
                    }, visualTransformation = PasswordVisualTransformation(),
                    /*Myös näppäimistön tyypiksi asetetaan salasana-tyyppi, jotta salasanat eivät tallennu
                    esimerkiksi ennakoivaan teksinsyöttöön */
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    //Nappi on aktiivinen vain, kun sekä username- ja password-kentissä on sisältöä
                    enabled = vm.loginState.value.username != "" && vm.loginState.value.password != "",
                    //Nappia painettaessa kutsutaan ViewModelin login-funktiota
                    onClick = {
                        vm.login()
                        //Kutsutaan callback-funktiota, jonka avulla toteutetaan navigointi oikeaan kohteeseen
                        //goToCategories()
                    }) {
                    Text(text = "Login")
                }
            }
        }
    }

}