package com.example.edistynytmobiili.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.viewmodel.LoginViewModel

@Composable
fun LoginScreen(){
    //viewModel huolehtii siitä, että tiedot säilytetään konfiguraatiomuutoksien yhteydessä
    val loginVm: LoginViewModel = viewModel()
    /*Jotta saadaan latausikoni keskitettyä, se tarvii jonkun viitteen, johon se vertaa, eikä tätä voi suoraan
    surfacesta tehdä. Lisätään siis box, joka pitää sisällään haluttu sisältö.
    Box keskitetään keskelle surfacea contentAlignmentillä*/
    Box(contentAlignment = Alignment.Center) {
        //Kun todetaan sovelluksen olevan lataustilassa, näytetään keskellä näyttöä latausikoni
        when {
            loginVm.loginState.value.loading -> CircularProgressIndicator(modifier = Modifier.align(
                Alignment.Center))
            //Muissa tapauksissa näytetään tavallinen sisältö; Username- ja Password-tekstikentät sekä Login-nappi
            else -> Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {//Tekstikentän valueksi saadaan LoginViewModelissa määritellyn loginStaten arvo,
                // joka rakennetaan Login data classin avulla
                OutlinedTextField(value = loginVm.loginState.value.username, onValueChange = { newUsername ->
                    //Tekstikentän arvon muuttuessa ViewModelista kutsutaan funktiota muuttamaan usernamea
                    loginVm.setUsername(newUsername)
                }, placeholder = {
                    Text(text="Username")
                })
                //Spacerillä lisätään haluttu määrä tilaa komponenttien välille
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = loginVm.loginState.value.password,
                    onValueChange = { newPassword ->
                        loginVm.setPassword(newPassword)
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
                    enabled = loginVm.loginState.value.username != "" && loginVm.loginState.value.password != "",
                    //Nappia painettaessa kutsutaan ViewModelin login-funktiota
                    onClick = { loginVm.login() }
                ) {
                    Text(text = "Login")
                }
            }
        }
    }

}