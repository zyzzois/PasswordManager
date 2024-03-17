package com.example.detail.di

import com.example.detail.domain.PasswordsRepository
import kotlin.properties.Delegates

interface DetailsDeps {
    val detailsRepository: PasswordsRepository
//    val context: Context
}

interface DetailsDepsProvider {
    val dependencies: DetailsDeps
    companion object : DetailsDepsProvider by DetailsDepsStore
}

object DetailsDepsStore: DetailsDepsProvider {
    override var dependencies: DetailsDeps by Delegates.notNull()
}