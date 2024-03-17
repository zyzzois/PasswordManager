package com.example.detail.di

import com.example.detail.presentation.DetailViewModel
import dagger.Component
import javax.inject.Scope

@DetailsScope
@Component(dependencies = [DetailsDeps::class])
internal interface DetailsComponent {
    fun getDetailsViewModel(): DetailViewModel

    @Component.Builder
    interface Builder {
        fun dependencies(deps: DetailsDeps): Builder
        fun build(): DetailsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DetailsScope
