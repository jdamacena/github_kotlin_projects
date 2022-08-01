package com.juniordamacena.bankuishtest.models

/*Created by junio on 28/05/2022.*/
data class RepositoryResponse(
    val incomplete_results: Boolean,
    val items: List<Repository>,
    val total_count: Int,
)