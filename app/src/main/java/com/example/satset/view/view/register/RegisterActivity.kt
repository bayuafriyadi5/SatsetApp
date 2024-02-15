package com.example.satset.view.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.satset.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val registerViewModel: RegisterViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            binding.loading.visibility = View.VISIBLE

            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Please insert your name"
                    binding.loading.visibility = View.INVISIBLE
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Please insert your email"
                    binding.loading.visibility = View.INVISIBLE
                }
                password.length < 8 -> {
                    Toast.makeText(
                        this,
                        "Password must or more than 8 character",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.loading.visibility = View.INVISIBLE
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Please insert your password"
                    binding.loading.visibility = View.INVISIBLE
                }
                else -> {
                    lifecycleScope.launchWhenResumed {
                        launch {
                            registerViewModel.registerUser(name, email, password)
                                .observe(this@RegisterActivity) { result ->
                                    result.onSuccess {
                                        binding.loading.visibility = View.INVISIBLE
                                        AlertDialog.Builder(this@RegisterActivity).apply {
                                            setTitle("Yeah!")
                                            setMessage("Your account has been successfully created ")
                                            setPositiveButton("Next") { _, _ ->
                                                finish()
                                            }
                                            create()
                                            show()
                                        }
                                    }
                                    result.onFailure {
                                        val error = it.message
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.loading.visibility = View.INVISIBLE
                                    }

                                }
                        }
                    }
                }
            }
        }
    }


}

