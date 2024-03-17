package com.example.core_data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "urls")
data class WebsiteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val domain: String,
    val websiteUrl: String,
    val iconUrl: String,
    val title: String
) {
    companion object{
        const val DEFAULT_ID = 0
    }
}