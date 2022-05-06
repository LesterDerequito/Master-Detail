package com.lesterderequito.masterdetailapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.lesterderequito.masterdetailapplication.databinding.ActivitySplashBinding
import com.lesterderequito.masterdetailapplication.view.PhotoActivity

const val SPLASH_TIME = 3000L

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent = Intent(this, PhotoActivity::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_TIME)

        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in)
        binding.imageViewLogo.animation = fadeInAnimation
    }

}