package com.example.detail.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.detail.data.PasswordsRepositoryImpl
import com.example.detail.domain.PasswordsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DetailsModule {
    @Binds
    fun bindPasswordsRepository(
        passwordsRepositoryImpl: PasswordsRepositoryImpl
    ): PasswordsRepository

    companion object {
        private const val FILE = "encryptprefs"

        @Provides
        fun provideSharedPreferences(context: Application): SharedPreferences {
            val masterKeyAlias = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            return EncryptedSharedPreferences.create(
                context,
                FILE,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

    }
}