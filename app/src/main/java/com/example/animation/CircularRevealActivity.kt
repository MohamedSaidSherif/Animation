package com.example.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.animation.databinding.ActivityCircularRevealBinding

class CircularRevealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCircularRevealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_circular_reveal)

        binding.activityCircularRevealButton.setOnClickListener {
            revealCircularRevealAnimation(binding.activityCircularRevealCheeseImageView)
        }

        binding.activityCircularHideButton.setOnClickListener {
            hideCircularRevealAnimation(binding.activityCircularRevealCheeseImageView)
        }
    }

    private fun revealCircularRevealAnimation(view: View) {
        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            val cx = view.width / 2
            val cy = view.height / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
            // make the view visible and start the animation
            view.visibility = View.VISIBLE
            anim.start()
        } else {
            // set the view to invisible without a circular reveal animation below Lollipop
            view.visibility = View.VISIBLE
        }

    }

    private fun hideCircularRevealAnimation(view: View) {
        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            val cx = view.width / 2
            val cy = view.height / 2

            // get the initial radius for the clipping circle
            val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animation (the final radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0f)

            // make the view invisible when the animation is done
            anim.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.INVISIBLE
                }
            })

            // start the animation
            anim.start()
        } else {
            // set the view to visible without a circular reveal animation below Lollipop
            view.visibility = View.INVISIBLE
        }

    }
}