package com.example.satset.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.satset.data.local.datastore.AuthPreferences
import com.example.satset.data.remote.response.ads.AdsResponse
import com.example.satset.data.remote.response.history.HistoryResponse
import com.example.satset.data.remote.response.LoginResponse
import com.example.satset.data.remote.response.ProfileResponse
import com.example.satset.data.remote.response.RegisterResponse
import com.example.satset.data.remote.response.queue.ViewQueueResponse
import com.example.satset.data.remote.retrofit.ApiService
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
    ) {

        suspend fun login(
            email: String,
            password: String
        ): LiveData<Result<LoginResponse>> = liveData {
            try {
                val response = apiService.login(email, password)
                emit(Result.success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                val errorMessage = when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            401 -> "Invalid email or password"
                            404 -> "User not found"
                            else -> "Login failed, please try again later."
                        }
                    }
                    is SocketTimeoutException -> "Connection timed out, please try again later."
                    else -> "Login failed, please try again later."
                }
                emit(Result.failure(Throwable(errorMessage, e)))
            }
        }

        suspend fun register(
            name: String,
            email: String,
            password: String
        ): LiveData<Result<RegisterResponse>> = liveData {
            try {
                val response = apiService.register(name, email, password)
                emit(Result.success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                val errorMessage = when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            400 -> "Email already taken"
                            else -> "Register failed, please try again later."
                        }
                    }
                    is SocketTimeoutException -> "Connection timed out, please try again later."
                    else -> "Register failed, please try again later."
                }
                emit(Result.failure(Throwable(errorMessage, e)))
            }
        }

    suspend fun getProfile(token: String): LiveData<ProfileResponse?> = liveData {
        try {
            val token = generateToken(token)
            val response = apiService.getProfile(token)
            emit(response) // Emit ProfileResponse directly
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null) // Error occurred, emit null
        }
    }

    suspend fun getAds(token: String): LiveData<AdsResponse?> = liveData {
        try {
            val token = generateToken(token)
            val response = apiService.getAds(token)
            emit(response) // Emit ProfileResponse directly
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null) // Error occurred, emit null
        }
    }

    suspend fun join(
        token: String,
        eventCode: String
    ): LiveData<Result<ViewQueueResponse>> = liveData {
            try {
                val token = generateToken(token)
                val response = apiService.join(token,eventCode)
                emit(Result.success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
    }

    suspend fun getQueue(token: String): LiveData<ViewQueueResponse?> = liveData {
        try {
            val token = generateToken(token)
            val response = apiService.getQueue(token)
            emit(response) // Emit ProfileResponse directly
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null) // Error occurred, emit null
        }
    }

    suspend fun getHistory(token: String): LiveData<HistoryResponse?> = liveData {
        try {
            val token = generateToken(token)
            val response = apiService.getHistory(token)
            emit(response) // Emit ProfileResponse directly
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null) // Error occurred, emit null
        }
    }

    fun getToken(): LiveData<String?> = authPreferences.getToken()
    private fun generateToken(token: String): String = "Bearer $token"

}