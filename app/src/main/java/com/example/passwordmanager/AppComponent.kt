package com.example.passwordmanager

import android.app.Application
import com.example.detail.di.DetailsDeps
import com.example.detail.di.DetailsModule
import com.example.detail.domain.PasswordsRepository
import com.example.websites.di.UrlsScreenDeps
import com.example.websites.di.UrlsScreenModule
import com.example.websites.domain.WebsitesRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@AppScope
@Component(modules = [DetailsModule::class, UrlsScreenModule::class])
interface AppComponent: DetailsDeps, UrlsScreenDeps {
    override val detailsRepository: PasswordsRepository
    //override val context: Context
    override val urlsRepository: WebsitesRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build() : AppComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope