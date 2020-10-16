package com.develop.recipes.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.develop.recipes.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    val timeOut=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val topAnimation=AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val middleAnimation=AnimationUtils.loadAnimation(this,R.anim.middle_animation)
        val bottomAnimation=AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        top_textview.startAnimation(topAnimation)
        middle_textview.startAnimation(middleAnimation)
        bottom_textview.startAnimation(bottomAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity,RecipesActivity::class.java))
            finish()
        },timeOut.toLong())

    }
}
