package com.example.detail.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class EncryptionManager @Inject constructor() {
    fun encryptPassword(decryptedPassword: String): Set<String> {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, generateKey())
        val encryptedByteValue = cipher.doFinal(
            decryptedPassword.toByteArray()
        )
        val encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)

        val ivAsString = Base64.encodeToString(cipher.iv, Base64.NO_WRAP)
        val set = linkedSetOf<String>(ivAsString, encryptedValue64)
        return set
    }

    fun decryptPassword(encryptedPassword: String, iv: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivSpec = IvParameterSpec(Base64.decode(iv, Base64.NO_WRAP))
        cipher.init(Cipher.DECRYPT_MODE, getKey(), ivSpec)
        val encryptedData = Base64.decode(encryptedPassword, Base64.DEFAULT)
        return String(cipher.doFinal(encryptedData), Charsets.UTF_8)
    }

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

        private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: generateKey()
    }

    private fun generateKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KEYSTORE_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val KEYSTORE_ALIAS = "aliassecret"
    }
}