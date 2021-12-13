package com.example.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.example.animation.databinding.ActivityCrossFadeBinding

class CrossFadeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CrossFadeActivity"
    }

    private lateinit var binding: ActivityCrossFadeBinding
    private var animationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cross_fade)

        animationDuration = resources.getInteger(android.R.integer.config_longAnimTime) * 10
        Log.d(TAG, "Animation Duration: $animationDuration")

        binding.content.visibility = View.GONE

        crossFade()
    }

    private fun crossFade() {
        binding.content.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(animationDuration.toLong())
                .setListener(null)
        }
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        binding.loadingSpinner.apply {
            animate()
                .alpha(0f)
                .setDuration(animationDuration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = View.GONE

                        FlingAnimation(binding.content, DynamicAnimation.SCROLL_Y).apply {
                            val pixelPerSecond: Float =
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5000f, resources.displayMetrics)
                            setStartVelocity(pixelPerSecond)
//                            setMinValue(0f)
//                            setMaxValue(maxScroll)
                            friction = 1.1f
                            start()
                        }
                    }
                })
        }
    }
}