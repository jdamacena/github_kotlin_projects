package com.juniordamacena.bankuishtest.repositories

import com.juniordamacena.bankuishtest.Repository

interface ReposRepository {
    suspend fun getRepositories(): List<Repository>
}
