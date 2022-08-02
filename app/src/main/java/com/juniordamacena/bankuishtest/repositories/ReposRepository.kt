package com.juniordamacena.bankuishtest.repositories

import com.juniordamacena.bankuishtest.models.Repository

interface ReposRepository {
    suspend fun getRepositories(pageNumber: Int, perPage: Int): List<Repository>
    suspend fun loadReadMeForRepository(repository: Repository) : String
}
