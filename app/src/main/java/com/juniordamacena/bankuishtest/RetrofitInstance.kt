package com.juniordamacena.bankuishtest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*Created by junio on 28/05/2022.*/
object RetrofitInstance {
    val api: RepositoriesApi by lazy {
        Retrofit.Builder()
            .baseUrl(" https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RepositoriesApi::class.java)
    }
}