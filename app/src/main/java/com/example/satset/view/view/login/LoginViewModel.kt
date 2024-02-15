package com.example.satset.view.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satset.data.local.datastore.AuthPreferences
import com.example.satset.data.remote.response.LoginResponse
import com.example.satset.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authPreferences: AuthPreferences
) : ViewModel() {
    suspend fun login(email: String, password: String): LiveData<Result<LoginResponse>> =
        userRepository.login(email, password)

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authPreferences.saveToken(token)
        }
    }

}