package com.example.tictactoe.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.tictactoe.R

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    // Declare the view binding variable
    protected lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createViewBinding()
        setContentView(binding.root)
        setupToolBar()
    }

    // Abstract function to create the view binding instance
    abstract fun createViewBinding(): Binding

    private fun setupToolBar() {
        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}