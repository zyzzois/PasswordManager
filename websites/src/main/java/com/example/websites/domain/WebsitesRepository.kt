package com.example.websites.domain

import com.example.core_data.models.PasswordModel

interface WebsitesRepository {
    suspend fun getAllPasswords(): List<PasswordModel>
}