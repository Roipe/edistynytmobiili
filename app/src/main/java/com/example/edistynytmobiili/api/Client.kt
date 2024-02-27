package com.example.edistynytmobiili.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Funktio, jolla luodaan jokaiselle api-paketin tiedostolle omat Retrofit-instanssit
fun createClient(): Retrofit {
    //baseUrliin määritetään backendin urlin alku Retrofitille käytettäväksi.
    //Jos tässä määritettäisiin osoitteeksi "localhost", retrofit ottaisi yhteyden emulaattoriin, ei tietokoneen localhostiin.
    //10.0.0.2 on siis localhost-osoite Androidissa. Backendin käyttöliittymästä käy myös ilmi, että kaikki sen osiot löytyvät /api/v1:n sisältä
    return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1/")
    //return Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com")
        //Tässä konfiguroidaan Retrofit käyttämään gsonia datan muuntamiseen Kotlin data class- ja JSON -muotojen välillä.
        //Käytännössä Gson muuntaa uloslähtevän datan Kotlin data classista JSON-muotoon, sisääntulevan taas JSON-muodosta Kotlin data classiksi.
        .addConverterFactory(GsonConverterFactory.create()).build()
}