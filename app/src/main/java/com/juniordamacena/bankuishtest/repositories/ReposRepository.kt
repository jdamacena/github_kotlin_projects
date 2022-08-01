package com.juniordamacena.bankuishtest.repositories

import com.juniordamacena.bankuishtest.models.Repository

interface ReposRepository {
    suspend fun getRepositories(): List<Repository>
}
