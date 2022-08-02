package com.juniordamacena.bankuishtest.services

import com.juniordamacena.bankuishtest.models.ReadMeResponse
import com.juniordamacena.bankuishtest.models.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/*Created by junio on 28/05/2022.*/
interface GithubApi {
    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15,
    ): Response<RepositoryResponse>

    @GET("/repos/{full_name}/readme")
    suspend fun getReadMeUrl(
        @Path("full_name", encoded = true) repositoryFullName: String,
    ): Response<ReadMeResponse>

    @GET
    suspend fun loadRawText(
        @Url readmeUrl: String,
    ): String
}