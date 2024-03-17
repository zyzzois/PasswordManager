package com.example.detail.utils

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object BiometricUtil {
    private fun biometricStatus(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
    }

    fun isBiometricReady(context: Context) =
        biometricStatus(context) == BiometricManager.BIOMETRIC_SUCCESS

    fun noBiometricHardware(context: Context) =
        biometricStatus(context) == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE

    fun noBiometricEnrolled(context: Context) =
        biometricStatus(context) == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED

    fun noBiometricUnsupported(context: Context) =
        biometricStatus(context) == BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED

    fun noBiometricSecurity(context: Context) =
        biometricStatus(context) == BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED

    fun noBiometricUnavailable(context: Context) =
        biometricStatus(context) == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE


    private fun getPromptInfo(
        title: String,
        description: String
    ): BiometricPrompt.PromptInfo {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setNegativeButtonText("Cancel")
        return promptInfo.build()
    }

    private fun createBiometricPrompt(
        activity: FragmentActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthSuccess(result)
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(activity, "auth failed for an unknown error", Toast.LENGTH_LONG).show()
            }
        }
        return BiometricPrompt(
            activity,
            executor,
            callback
        )
    }

    fun showBiometricPrompt(
        title: String = "auth",
        description: String = "description",
        activity: FragmentActivity,
        listener: BiometricAuthListener
    ) {
        val promptInfo = getPromptInfo(title, description)
        val biometricPrompt = createBiometricPrompt(activity, listener)
        biometricPrompt.authenticate(promptInfo)
    }
}