package com.example.core_data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UrlsDao {

    @Query("SELECT * FROM urls")
    fun getSavedUrls(): List<WebsiteEntity>

    @Insert
    suspend fun insertNewUrl(url: WebsiteEntity)

    @Delete
    suspend fun deleteUrl(url: WebsiteEntity)

}