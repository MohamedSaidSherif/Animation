package com.example.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.animation.databinding.ActivitySpringAnimationBinding

class SpringAnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpringAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spring_animation)

        binding.activitySpringAnimationButton.setOnClickListener {
            SpringAnimation(
                binding.activitySpringAnimationImageView,
                DynamicAnimation.TRANSLATION_Y,
                500f
            ).apply {
                spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                spring.stiffness = SpringForce.STIFFNESS_HIGH
                start()
                addEndListener { animation, canceled, value, velocity ->
                    SpringAnimation(
                        binding.activitySpringAnimationImageView,
                        DynamicAnimation.TRANSLATION_Y,
                        100f
                    ).start()
                }
            }
        }
    }
}