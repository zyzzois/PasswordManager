package com.example.detail.domain

import com.example.core_data.models.PasswordModel
import javax.inject.Inject

class EditPasswordUseCase @Inject constructor(
    private val repository: PasswordsRepository
) {
    suspend operator fun invoke(password: PasswordModel) {
        repository.editPassword(password)
    }
}