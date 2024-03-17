package com.example.detail.utils

import androidx.biometric.BiometricPrompt

interface BiometricAuthListener {
    fun onBiometricAuthSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthError(errorCode: Int, errorMessage: String)
}