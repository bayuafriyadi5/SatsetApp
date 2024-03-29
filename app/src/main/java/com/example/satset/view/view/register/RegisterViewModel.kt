package com.example.satset.view.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.satset.data.remote.response.RegisterResponse
import com.example.satset.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    suspend fun registerUser(
        name: String, email: String, password: String
    ): LiveData<Result<RegisterResponse>> = userRepository.register(name, email, password)

}