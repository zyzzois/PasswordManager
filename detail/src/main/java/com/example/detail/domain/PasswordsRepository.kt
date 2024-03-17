package com.example.detail.domain

import com.example.core_data.models.PasswordModel

interface PasswordsRepository {
    suspend fun getPasswordByUrl(url: String): PasswordModel
    suspend fun editPassword(password: PasswordModel)
    suspend fun deletePassword(password: PasswordModel)
    suspend fun savePassword(password: PasswordModel)
}