package com.example.animation

import android.animation.*
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var cheeseAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ValueAnimator.ofFloat(0f, 600f).apply {
            duration = 3000
            addUpdateListener { updatedAnimation ->
                binding.activityMainLeftTextView.translationX =
                    updatedAnimation.animatedValue as Float
                binding.activityMainLeftTextView.translationY =
                    updatedAnimation.animatedValue as Float * 2
            }
            start()
        }

        ObjectAnimator.ofFloat(binding.activityMainRightTextView, "translationX", 0f, -600f).apply {
            duration = 3000
            start()
        }
        val forward =
            ObjectAnimator.ofFloat(binding.activityMainRightTextView, "translationY", 0f, 1200f)
                .apply {
                    duration = 3000
                    start()
                }


        val backLeftAnimation = ValueAnimator.ofFloat(600f, 0f).apply {
            duration = 3000
            addUpdateListener { updatedAnimation ->
                binding.activityMainLeftTextView.translationX =
                    updatedAnimation.animatedValue as Float
                binding.activityMainLeftTextView.translationY =
                    updatedAnimation.animatedValue as Float * 2
            }
        }

        val backRightXAnimation =
            ObjectAnimator.ofFloat(binding.activityMainRightTextView, "translationX", -600f, 0f)
                .apply {
                    duration = 3000
                }
        val backRightYAnimation =
            ObjectAnimator.ofFloat(binding.activityMainRightTextView, "translationY", 1200f, 0f)
                .apply {
                    duration = 3000
                }

        AnimatorSet().apply {
            play(forward).before(backLeftAnimation)
            play(backLeftAnimation).before(backRightXAnimation)
            play(backRightXAnimation).with(backRightYAnimation)
            start()

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    Log.d(TAG, "onAnimationEnd")
                    binding.activityMainLeftTextView.visibility = View.GONE
                    binding.activityMainRightTextView.visibility = View.GONE
                }
            })
        }

        binding.activityMainStateListAnimatorTestButton.setOnClickListener {
            it.animate().alpha(0.5f)
        }

        binding.activityMainCheeseImageView.setBackgroundResource(R.drawable.cheese_animation)
        cheeseAnimation = binding.activityMainCheeseImageView.background as AnimationDrawable
        binding.activityMainCheeseImageView.setOnClickListener {
            cheeseAnimation.start()
        }

        binding.activityMainCrosseFadeAnimationButton.setOnClickListener {
            startActivity(Intent(this, CrossFadeActivity::class.java))
        }

        binding.activityMainCardFlipAnimationButton.setOnClickListener {
            startActivity(Intent(this, CardFlipActivity::class.java))
        }

        binding.activityMainCircularRevealAnimationButton.setOnClickListener {
            startActivity(Intent(this, CircularRevealActivity::class.java))
        }

        binding.activityMainCurvedMotionButton.setOnClickListener {
            startActivity(Intent(this, CurvedMotionActivity::class.java))
        }

        binding.activityMainZoomAnimationButton.setOnClickListener {
            startActivity(Intent(this, ZoomAnimationActivity::class.java))
        }

        binding.activityMainSpringAnimationButton.setOnClickListener {
            startActivity(Intent(this, SpringAnimationActivity::class.java))
        }

        binding.activityMainAnimationUsingSceneButton.setOnClickListener {
            startActivity(
                Intent(this, AnimationUsingSceneActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }

        binding.activityMainScreenSlidePagerTestButton.setOnClickListener {
            startActivity(
                Intent(this, ScreenSlidePagerActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }
}