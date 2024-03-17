package com.example.websites.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.core_data.database.AppDatabase
import com.example.core_data.database.UrlsDao
import com.example.core_data.di.ViewModelKey
import com.example.websites.data.WebsitesRepositoryImpl
import com.example.websites.domain.WebsitesRepository
import com.example.websites.presentation.UrlsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface UrlsScreenModule {
    @Binds
    fun bindUrlsRepository(
        urlsRepositoryImpl: WebsitesRepositoryImpl
    ): WebsitesRepository

    @IntoMap
    @Binds
    @ViewModelKey(UrlsViewModel::class)
    fun bindUrlsViewModel(viewModel: UrlsViewModel): ViewModel

    companion object {
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        fun provideDao(
            application: Application
        ): UrlsDao {
            return AppDatabase.getInstance(application).urlsDao()
        }
    }
}