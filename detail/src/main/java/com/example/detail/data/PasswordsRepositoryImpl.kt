package com.example.detail.data

import android.content.SharedPreferences
import com.example.core_data.database.UrlsDao
import com.example.core_data.models.DataMapper
import com.example.core_data.models.PasswordModel
import com.example.detail.domain.PasswordsRepository
import javax.inject.Inject

class PasswordsRepositoryImpl @Inject constructor(
    private val encryptionManager: EncryptionManager,
    private val urlsDao: UrlsDao,
    private val dataMapper: DataMapper,
    private val sharedPreferences: SharedPreferences
): PasswordsRepository {
    override suspend fun getPasswordByUrl(url: String): PasswordModel {
        val encryptedPassword = sharedPreferences.getStringSet(url, setOf())
        val decryptedPassword = encryptionManager.decryptPassword(
            encryptedPassword = encryptedPassword?.last() ?: "",
            iv = encryptedPassword?.first() ?: ""
        )

        return PasswordModel(
            websiteUrl = url,
            password = decryptedPassword,
            title = "Временно рандомный тайтл"
        )
    }

    override suspend fun editPassword(password: PasswordModel) {
    }

    override suspend fun deletePassword(password: PasswordModel) {
    }

    override suspend fun savePassword(password: PasswordModel) {
        val encryptedPassword = encryptionManager.encryptPassword(password.password!!)
        sharedPreferences.edit().putStringSet(
            password.websiteUrl, encryptedPassword
        ).apply()
        urlsDao.insertNewUrl(
            dataMapper.mapModelToEntity(password)
        )
    }

}
