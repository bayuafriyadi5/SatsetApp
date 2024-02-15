package com.example.satset.view.view.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.paging.ExperimentalPagingApi
import com.example.satset.databinding.ActivitySplashBinding
import com.example.satset.view.GuideActivity
import com.example.satset.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1500 //3 seconds

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val splashViewModel: SplashViewModel by viewModels ()
    private var token: String = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        splashViewModel.checkIfTokenAvailable().observe(this) { token ->
            this.token = token ?: "null"

        }
        supportActionBar?.hide()
        Handler().postDelayed(Runnable {
            checkIfSessionValid()
            finish()
        }, SPLASH_TIME_OUT.toLong())

        playAnimation()

    }

    private fun checkIfSessionValid() {
        if (token == "null") {
            val intent = Intent(this, GuideActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            splashViewModel.setFirstTime(true)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            splashViewModel.setFirstTime(false)
            finish()
        }

    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.logoBakso, View.ALPHA, 1f).setDuration(1000)
        AnimatorSet().apply {
            playSequentially(logo)
            start()
        }
    }
}