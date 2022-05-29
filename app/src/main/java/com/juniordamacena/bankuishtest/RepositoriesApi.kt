package com.juniordamacena.bankuishtest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*Created by junio on 28/05/2022.*/
interface RepositoriesApi {
    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15
    ): Response<RepositoryResponse>
}