package com.example.websites.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.models.PasswordModel
import com.example.websites.domain.GetAllPasswordsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UrlsViewModel @Inject constructor(
    private val getAllPasswordsUseCase: GetAllPasswordsUseCase
): ViewModel() {

    private val _websites = MutableLiveData<List<PasswordModel>?>()
    val websites: MutableLiveData<List<PasswordModel>?>
        get() = _websites

    init {
        updateList()
    }

    fun updateList() {
        viewModelScope.launch {
            _websites.value = getAllPasswordsUseCase()
        }
    }


}