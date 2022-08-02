package com.juniordamacena.bankuishtest.di

import com.juniordamacena.bankuishtest.repositories.ReposRepository
import com.juniordamacena.bankuishtest.repositories.ReposRepositoryImpl
import com.juniordamacena.bankuishtest.services.GithubApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/*Created by junio on 24/07/2022.*/
val appModule = module {
    single<GithubApi> {
        Retrofit.Builder()
            .baseUrl(" https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    single<GithubApi>(named(name = "githubApiScalars")) {
        Retrofit.Builder()
            .baseUrl(" https://api.github.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    single<ReposRepository> { ReposRepositoryImpl(get(), get(named(name = "githubApiScalars"))) }
}