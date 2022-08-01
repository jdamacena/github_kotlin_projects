package com.juniordamacena.bankuishtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.bankuishtest.models.Repository
import com.juniordamacena.bankuishtest.repositories.ReposRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/*Created by junio on 28/05/2022.*/
class RepositoriesViewModel : ViewModel(), KoinComponent {
    private val reposRepository: ReposRepository by inject()

    private val _repositories: MutableLiveData<List<Repository>> = MutableLiveData()

    val repositories: LiveData<List<Repository>>
        get() = _repositories

    var selectedId: Int = -1

    fun loadRepositories() {
        viewModelScope.launch {
            _repositories.value = reposRepository.getRepositories()
        }
    }
}