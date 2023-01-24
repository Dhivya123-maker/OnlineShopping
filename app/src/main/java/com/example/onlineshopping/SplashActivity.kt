package com.example.onlineshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var text : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageView = findViewById(R.id.SplashScreenImage)
        text = findViewById(R.id.textview)


        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        imageView.startAnimation(slideAnimation)
        text.startAnimation(slideAnimation)


        Handler().postDelayed({
            val i = Intent(this@SplashActivity, ProductActivity::class.java)
            startActivity(i)
        }, 3500)

    }
}