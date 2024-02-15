package com.example.satset.view.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.satset.data.local.datastore.AuthPreferences
import com.example.satset.data.remote.response.ProfileResponse
import com.example.satset.data.remote.retrofit.ApiService
import com.example.satset.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun getProfile(token: String): LiveData<ProfileResponse?> =
        userRepository.getProfile(token)

    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authPreferences.deleteToken()
        }
    }

}