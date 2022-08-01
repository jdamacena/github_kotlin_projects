package com.juniordamacena.bankuishtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.recyclerview.widget.DiffUtil
import com.juniordamacena.bankuishtest.models.Repository
import com.juniordamacena.bankuishtest.repositories.ReposPagingSource
import com.juniordamacena.bankuishtest.repositories.ReposRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/*Created by junio on 28/05/2022.*/
class RepositoriesViewModel : ViewModel(), KoinComponent {
    private val reposRepository: ReposRepository by inject()

    private var reposPagingSource: ReposPagingSource? = null

    private fun pagingSourceFactory(): ReposPagingSource {
        reposPagingSource = ReposPagingSource(reposRepository)
        return reposPagingSource!!
    }

    val pagedData = Pager(PagingConfig(pageSize = 20, initialLoadSize = 80)) {
        pagingSourceFactory()
    }.liveData.cachedIn(viewModelScope)

    private var _selectedItem: MutableLiveData<Repository> = MutableLiveData()

    val selectedItem: LiveData<Repository>
        get() = _selectedItem

    fun setSelectedItem(value: Repository) {
        _selectedItem.value = value
    }

    /**
     * Invalidates the [ReposPagingSource] so that it can start loading again, refreshing its data
     */
    fun invalidatePagingSource() {
        reposPagingSource?.invalidate()
    }

    object RepositoryComparator : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}