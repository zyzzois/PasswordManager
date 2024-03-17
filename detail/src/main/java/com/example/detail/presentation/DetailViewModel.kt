package com.example.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.models.PasswordModel
import com.example.detail.domain.GetPasswordByUrlUseCase
import com.example.detail.domain.SavePasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val savePasswordUseCase: SavePasswordUseCase,
    private val getPasswordUseCase: GetPasswordByUrlUseCase
): ViewModel() {
    val isEditMode = MutableLiveData(false)

    private val _errorInputUrl = MutableLiveData<Boolean>()
    val errorInputUrl: LiveData<Boolean>
        get() = _errorInputUrl

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorInputTitle = MutableLiveData<Boolean>()
    val errorInputTitle: LiveData<Boolean>
        get() = _errorInputTitle

    private val _password = MutableLiveData<PasswordModel>()
    val password: LiveData<PasswordModel>
        get() = _password

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private fun getPassword(url: String) {
        viewModelScope.launch {
            val password = getPasswordUseCase(url)
            _password.value = password
        }
    }

    fun addPassword(inputUrl: String?, inputPassword: String?, inputTitle: String?) {
        val url = parseUrl(inputUrl)
        val password = parsePassword(inputPassword)
        val title = parseTitle(inputTitle)
        val fieldsValid = validateInput(url, password, title)
        if (fieldsValid) {
            viewModelScope.launch {
                savePasswordUseCase(
                    PasswordModel(
                        title = title,
                        websiteUrl = url,
                        password = password
                    )
                )
            }
            finishWork()
        }
    }

    private fun parseUrl(inputUrl: String?): String {
        return inputUrl?.trim() ?: ""
    }

    private fun parsePassword(inputPassword: String?): String {
        return inputPassword?.trim() ?: ""
    }

    private fun parseTitle(inputTitle: String?): String {
        return inputTitle?.trim() ?: ""
    }

    private fun validateInput(url: String, password: String, title: String): Boolean {
        var result = true
        if (url.isBlank()) {
            _errorInputUrl.value = true
            result = false
        } else {
            val urlRegex = URL_VALIDATION_PATTERN.toRegex()

            if (!url.matches(urlRegex)) {
                _errorInputUrl.value = true
                result = false
            }
        }
        if (password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }
        if (title.isBlank()) {
            _errorInputTitle.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputUrl() {
        _errorInputUrl.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    fun resetErrorInputTitle() {
        _errorInputTitle.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    fun parseArgFromStringToModel(args: String) {
        getPassword(args)
    }

    companion object {
        private const val URL_VALIDATION_PATTERN = """^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$"""
    }
}