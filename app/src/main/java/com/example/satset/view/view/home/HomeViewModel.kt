package com.example.satset.view.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.satset.data.local.datastore.AuthPreferences
import com.example.satset.data.remote.response.ads.AdsResponse
import com.example.satset.data.remote.response.ProfileResponse
import com.example.satset.data.remote.response.queue.ViewQueueResponse
import com.example.satset.data.remote.retrofit.ApiService
import com.example.satset.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    private val userRepository: UserRepository,
) : ViewModel() {

    // Get the profile using the provided token
    suspend fun getProfile(token: String): LiveData<ProfileResponse?> =
        userRepository.getProfile(token)

    suspend fun getAds(token: String): LiveData<AdsResponse?> =
        userRepository.getAds(token)

    suspend fun getQueue(token: String): LiveData<ViewQueueResponse?> =
        userRepository.getQueue(token)

    suspend fun joinQueue(token: String, eventCode: String): LiveData<Result<ViewQueueResponse>> {
        return userRepository.join(token, eventCode)
    }

    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()
}
