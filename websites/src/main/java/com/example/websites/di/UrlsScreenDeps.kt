package com.example.websites.di

import com.example.websites.domain.WebsitesRepository
import kotlin.properties.Delegates

interface UrlsScreenDeps {
    val urlsRepository: WebsitesRepository
}

interface UrlsScreenDepsProvider {
    val dependencies: UrlsScreenDeps
    companion object : UrlsScreenDepsProvider by UrlsScreenDepsStore
}

object UrlsScreenDepsStore: UrlsScreenDepsProvider {
    override var dependencies: UrlsScreenDeps by Delegates.notNull()
}

