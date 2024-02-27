package com.example.edistynytmobiili.api

import com.example.edistynytmobiili.model.AuthReqModel
import com.example.edistynytmobiili.model.LoginResModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

//Kutsu Client.kt-tiedoston funktiolle
private val retrofit = createClient()

//Login-funktion kutsua varten tehdään AuthApi-interfacesta muuttuja, tätä kutsutaan ViewModelissa.
val authService = retrofit.create(AuthApi::class.java)

//Jokaista rajapintakutsua kohden tehdään interface
interface AuthApi {

    // @POST tarkoittaa, että retrtofit lähettää requestin POST-metodia käyttäen
    //Tämä osoite käytännössä muotoa:
    // baseUrl + auth/login
    // eli -> "http://10.0.2.2:8000/api/v1/auth/login"
    @POST("auth/login")
    //Mikäli hausta ei kuulu tuloksia riittävän ajan kuluttua, supsend funktiolla se voidaan keskeyttää
    //Tätä funktiota kutsutaan myöhemmin LoginViewModelista nimellä "login".
    //request bodyn sisällön tietotyyppi on AuthReq (username & password)

    //Vastauksena saadaan Login-modeltiedostossa määritellyn LoginResModel data classin muotoisena
    suspend fun login(@Body authReq: AuthReqModel): LoginResModel
}
