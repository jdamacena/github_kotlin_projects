package com.juniordamacena.bankuishtest.repositories

import android.util.Log
import com.juniordamacena.bankuishtest.fragments.TAG
import com.juniordamacena.bankuishtest.models.Repository
import com.juniordamacena.bankuishtest.services.GithubApi
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 20/07/2022.*/
class ReposRepositoryImpl(
    private val githubApiJson: GithubApi,
    private val githubApiScalars: GithubApi,
) : ReposRepository {

    override suspend fun getRepositories(pageNumber: Int, perPage: Int): List<Repository> {
        var repositories: List<Repository> = listOf()

        val response = try {
            githubApiJson.getRepositories("language:kotlin", page = pageNumber, perPage = perPage)
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
            } ?: emptyList()
        } else {
            Log.e(TAG, "Response was not successful")
        }

        return repositories
    }

    override suspend fun loadReadMeForRepository(repository: Repository): String {
        var readmeText = ""

        try {
            val getUrlResponse = githubApiJson.getReadMeUrl(repository.full_name)
            val readmeUrl = getUrlResponse.body()?.download_url ?: return readmeText

            readmeText = githubApiScalars.loadRawText(readmeUrl)
        } catch (e: IOException) {
            Log.e(TAG, e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e(TAG, e.message ?: "HttpException")
        }

        return readmeText
    }
}