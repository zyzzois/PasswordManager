package com.example.core_data.models

data class PasswordModel(
    val title: String,
    val websiteUrl: String,
    val password: String? = null,
    val iconUrl: String? = null,
)