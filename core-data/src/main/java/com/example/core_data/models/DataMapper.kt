package com.example.core_data.models

import com.example.core_data.database.WebsiteEntity
import javax.inject.Inject

class DataMapper @Inject constructor() {
    fun mapEntityToMode(urlEntity: WebsiteEntity) = PasswordModel(
        websiteUrl = urlEntity.websiteUrl,
        password = null,
        iconUrl = urlEntity.iconUrl,
        title = urlEntity.title
    )

    fun mapModelToEntity(model: PasswordModel) = WebsiteEntity(
        websiteUrl = model.websiteUrl,
        iconUrl = model.websiteUrl + "/favicon.ico",
        id = WebsiteEntity.DEFAULT_ID,
        domain = model.websiteUrl,
        title = model.title
    )
}