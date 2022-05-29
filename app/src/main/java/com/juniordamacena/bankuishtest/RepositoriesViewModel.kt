package com.juniordamacena.bankuishtest

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 28/05/2022.*/
class RepositoriesViewModel : ViewModel() {
    private val repositories: MutableLiveData<List<Repository>> by lazy {
        MutableLiveData<List<Repository>>().also {
            runBlocking {
                launch {
                    it.value = loadRepositories()
                }
            }
        }
    }

    var selectedId: Int = -1

    fun getRepositories(refresh: Boolean): LiveData<List<Repository>> {
        if (refresh) {
            runBlocking {
                launch {
                    repositories.value = loadRepositories()
                }
            }
        }

        return repositories
    }

    /**
     * Loads the list of repositories from the API
     */
    private suspend fun loadRepositories(): List<Repository> {
        var repositories: List<Repository> = listOf()

        val response = try {
            RetrofitInstance.api.getRepositories("language:kotlin", perPage = 100)
        } catch (e: IOException) {
            Log.e(TAG, e.message ?: "IOException")
            return emptyList()
        } catch (e: HttpException) {
            Log.e(TAG, e.message ?: "HttpException")
            return emptyList()
        }

        if (response.isSuccessful && response.body() != null) {
            repositories = response.body()?.items?.filter {
                repository -> repository.language.lowercase() == "kotlin"
            }!!
        } else {
            Log.e(TAG, "Response was not successful")
        }

        return repositories
    }
}