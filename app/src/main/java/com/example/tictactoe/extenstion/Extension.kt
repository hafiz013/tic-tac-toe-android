package com.example.tictactoe.extenstion

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import com.example.tictactoe.R

fun Context.showAnimatedToast(message: String) {
    // Inflate the custom layout
    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(R.layout.custom_toast,null)

    // Set the message
    val textView = layout.findViewById<TextView>(R.id.custom_toast_text)
    textView.text = message

    // Create a toast with the custom layout
    val toast = Toast(applicationContext)
    toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout

    // Apply animation to the toast
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.duration = 1000
    fadeIn.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            // Add any additional actions after the animation ends
        }
    })
    layout.startAnimation(fadeIn)

    // Show the toast
    toast.show()
}
