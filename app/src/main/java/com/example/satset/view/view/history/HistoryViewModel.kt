package com.example.satset.view.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.satset.data.local.datastore.AuthPreferences
import com.example.satset.data.remote.response.history.HistoryResponse
import com.example.satset.data.remote.retrofit.ApiService
import com.example.satset.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    private val userRepository: UserRepository,
) : ViewModel() {

    // Get the profile using the provided token
    suspend fun getHistory(token: String): LiveData<HistoryResponse?> =
        userRepository.getHistory(token)

    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()
}