package com.example.animation

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.animation.databinding.ActivityCurvedMotionBinding

class CurvedMotionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurvedMotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_curved_motion)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val path = Path().apply {
                arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
            }
            val animator = ObjectAnimator.ofFloat(binding.activityCurvedMotionBallImageView, View.X, View.Y, path).apply {
                duration = 2000
                start()
            }
        } else {
            // Create animator without using curved path
        }
    }
}