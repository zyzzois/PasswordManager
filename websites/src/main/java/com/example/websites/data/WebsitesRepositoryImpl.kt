package com.example.websites.data

import com.example.core_data.database.UrlsDao
import com.example.core_data.models.DataMapper
import com.example.core_data.models.PasswordModel
import com.example.websites.domain.WebsitesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WebsitesRepositoryImpl @Inject constructor(
    private val urlsDao: UrlsDao,
    private val dataMapper: DataMapper
): WebsitesRepository {
    override suspend fun getAllPasswords(): List<PasswordModel> {
        return withContext(Dispatchers.IO) {
            val dbList = urlsDao.getSavedUrls()
            val modelList = dbList.map {
                dataMapper.mapEntityToMode(it)
            }
            modelList
        }
    }
}