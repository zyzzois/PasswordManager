package com.example.detail.domain

import com.example.core_data.models.PasswordModel
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
    private val repository: PasswordsRepository
) {
    suspend operator fun invoke(password: PasswordModel) {
        repository.savePassword(password)
    }
}