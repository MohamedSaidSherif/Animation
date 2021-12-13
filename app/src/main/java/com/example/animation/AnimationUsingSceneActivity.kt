package com.example.animation

import android.animation.Animator
import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.animation.databinding.ActivityAnimationUsingSceneBinding
import android.animation.ValueAnimator

import android.graphics.drawable.ColorDrawable

import android.graphics.drawable.Drawable
import android.transition.*
import android.util.Log
import android.view.View


class AnimationUsingSceneActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "AnimationUsingSceneActi"
    }

    private lateinit var binding: ActivityAnimationUsingSceneBinding
    private lateinit var mScenes: Array<Scene>
    private var mCurrentScene = 0
    private lateinit var colorScenes: Array<Scene>
    private var colorCurrentScene = 0
    private lateinit var customTransition: CustomTransition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animation_using_scene)

        mScenes = arrayOf(
            Scene.getSceneForLayout(binding.rootScene, R.layout.first_scene, this),
            Scene.getSceneForLayout(binding.rootScene, R.layout.second_scene, this)
        )
        val fadeTransition: Transition =
            TransitionInflater.from(this)
                .inflateTransition(R.transition.fade_out_in)
        findViewById<Button>(R.id.switch_scene_button).setOnClickListener {
            mCurrentScene = (mCurrentScene + 1) % mScenes.size
            Log.d(TAG, "Transitioning to scene #$mCurrentScene")
            // Pass the custom Transition as second argument for TransitionManager.go
            // Pass the custom Transition as second argument for TransitionManager.go
            TransitionManager.go(mScenes[mCurrentScene], fadeTransition)
        }

        colorScenes = arrayOf(
            Scene.getSceneForLayout(binding.colorRootScene, R.layout.scene1, this),
            Scene.getSceneForLayout(binding.colorRootScene, R.layout.scene2, this),
            Scene.getSceneForLayout(binding.colorRootScene, R.layout.scene3, this)
        )
        customTransition = CustomTransition()
        findViewById<Button>(R.id.switch_scene_using_custom_transition_button).setOnClickListener {
            colorCurrentScene = (colorCurrentScene + 1) % colorScenes.size
            Log.d(TAG, "Transitioning to scene #$colorCurrentScene")
            // Pass the custom Transition as second argument for TransitionManager.go
            // Pass the custom Transition as second argument for TransitionManager.go
            TransitionManager.go(colorScenes[colorCurrentScene], customTransition)
        }
    }

    private class CustomTransition : Transition() {

        companion object {
            // Define a key for storing a property value in
            // TransitionValues.values with the syntax
            // package_name:transition_class:property_name to avoid collisions
            private val PROPNAME_BACKGROUND =
                "com.example.android.customtransition:CustomTransition:background56565"

        }

        override fun captureStartValues(transitionValues: TransitionValues) {
            captureValues(transitionValues)
        }

        override fun captureEndValues(transitionValues: TransitionValues) {
            captureValues(transitionValues)
        }


        override fun createAnimator(
            sceneRoot: ViewGroup,
            startValues: TransitionValues?,
            endValues: TransitionValues?
        ): Animator? {

            // This transition can only be applied to views that are on both starting and ending scenes.
            // This transition can only be applied to views that are on both starting and ending scenes.
            if (null == startValues || null == endValues) {
                return null
            }
            // Store a convenient reference to the target. Both the starting and ending layout have the
            // same target.
            // Store a convenient reference to the target. Both the starting and ending layout have the
            // same target.
            val view: View = endValues.view
            // Store the object containing the background property for both the starting and ending
            // layouts.
            // Store the object containing the background property for both the starting and ending
            // layouts.
            val startBackground = startValues.values[PROPNAME_BACKGROUND] as Drawable?
            val endBackground = endValues.values[PROPNAME_BACKGROUND] as Drawable?
            // This transition changes background colors for a target. It doesn't animate any other
            // background changes. If the property isn't a ColorDrawable, ignore the target.
            // This transition changes background colors for a target. It doesn't animate any other
            // background changes. If the property isn't a ColorDrawable, ignore the target.
            if (startBackground is ColorDrawable && endBackground is ColorDrawable) {
                val startColor = startBackground
                val endColor = endBackground
                // If the background color for the target in the starting and ending layouts is
                // different, create an animation.
                if (startColor.color != endColor.color) {
                    // Create a new Animator object to apply to the targets as the transitions framework
                    // changes from the starting to the ending layout. Use the class ValueAnimator,
                    // which provides a timing pulse to change property values provided to it. The
                    // animation runs on the UI thread. The Evaluator controls what type of
                    // interpolation is done. In this case, an ArgbEvaluator interpolates between two
                    // #argb values, which are specified as the 2nd and 3rd input arguments.
                    val animator = ValueAnimator.ofObject(
                        ArgbEvaluator(),
                        startColor.color, endColor.color
                    )
                    // Add an update listener to the Animator object.
                    animator.addUpdateListener { animation ->
                        val value = animation.animatedValue
                        // Each time the ValueAnimator produces a new frame in the animation, change
                        // the background color of the target. Ensure that the value isn't null.
                        if (null != value) {
                            view.setBackgroundColor(value as Int)
                        }
                    }
                    animator.duration = 800
                    // Return the Animator object to the transitions framework. As the framework changes
                    // between the starting and ending layouts, it applies the animation you've created.
                    return animator
                }
            }
            // For non-ColorDrawable backgrounds, we just return null, and no animation will take place.
            // For non-ColorDrawable backgrounds, we just return null, and no animation will take place.
            return null
        }


        // For the view in transitionValues.view, get the values you
        // want and put them in transitionValues.values
        private fun captureValues(transitionValues: TransitionValues) {
            // Get a reference to the view
            val view = transitionValues.view
            // Store its background property in the values map
            transitionValues.values[PROPNAME_BACKGROUND] = view.background
        }
    }
}