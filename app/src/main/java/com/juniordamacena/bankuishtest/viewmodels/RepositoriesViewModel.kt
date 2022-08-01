package com.juniordamacena.bankuishtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.bankuishtest.Repository
import com.juniordamacena.bankuishtest.repositories.ReposRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/*Created by junio on 28/05/2022.*/
class RepositoriesViewModel : ViewModel(), KoinComponent {
    private val reposRepository: ReposRepository by inject()

    private val repositories: MutableLiveData<List<Repository>> by lazy {
        MutableLiveData<List<Repository>>().also {
            viewModelScope.launch {
                it.value = reposRepository.getRepositories()
            }
        }
    }

    var selectedId: Int = -1

    fun getRepositories(refresh: Boolean): LiveData<List<Repository>> {
        if (refresh) {
            viewModelScope.launch {
                repositories.value = reposRepository.getRepositories()
            }
        }

        return repositories
    }
}