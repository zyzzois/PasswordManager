package com.example.detail.domain

import com.example.core_data.models.PasswordModel
import javax.inject.Inject

class GetPasswordByUrlUseCase @Inject constructor(
    private val repository: PasswordsRepository
) {
    suspend operator fun invoke(url: String): PasswordModel {
        return repository.getPasswordByUrl(url)
    }
}