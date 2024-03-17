package com.example.websites.di

import com.example.websites.presentation.UrlsViewModel
import dagger.Component
import javax.inject.Scope

@FavoriteScreenScope
@Component(
    dependencies = [
        UrlsScreenDeps::class
    ]
)
interface UrlsScreenComponent {
    fun getUrlsViewModel(): UrlsViewModel

    @Component.Builder
    interface Builder {
        fun dependencies(dependencies: UrlsScreenDeps): Builder
        fun build(): UrlsScreenComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FavoriteScreenScope