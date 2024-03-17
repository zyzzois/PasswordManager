package com.example.websites.domain

import javax.inject.Inject

class GetAllPasswordsUseCase @Inject constructor(
    private val repository: WebsitesRepository
) {
    suspend operator fun invoke(): List<com.example.core_data.models.PasswordModel> {
        return repository.getAllPasswords()
    }
}