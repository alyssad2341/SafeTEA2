package com.example.safetea2.api

data class OpenCageResponse(
    val results: List<Result>
)

data class Result(
    val components: Components
)

data class Components(
    val city: String?,
    val state: String?,
    val country: String?,
    val state_code: String?
)