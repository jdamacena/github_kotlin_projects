package com.juniordamacena.bankuishtest.repositories

import android.util.Log
import com.juniordamacena.bankuishtest.Repository
import com.juniordamacena.bankuishtest.fragments.TAG
import com.juniordamacena.bankuishtest.services.GithubApi
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 20/07/2022.*/
class ReposRepositoryImpl(
    private val githubApi: GithubApi,
) : ReposRepository {

    override suspend fun getRepositories(): List<Repository> {
        var repositories: List<Repository> = listOf()

        val response = try {
            githubApi.getRepositories("language:kotlin", perPage = 100)
        } catch (e: IOException) {
            Log.e(TAG, e.message ?: "IOException")
            return emptyList()
        } catch (e: HttpException) {
            Log.e(TAG, e.message ?: "HttpException")
            return emptyList()
        }

        if (response.isSuccessful && response.body() != null) {
            repositories = response.body()?.items?.filter { repository ->
                repository.language.lowercase() == "kotlin"
            }!!
        } else {
            Log.e(TAG, "Response was not successful")
        }

        return repositories
    }
}