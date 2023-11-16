package com.example.tictactoe.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.viewbinding.ViewBinding
import com.example.tictactoe.R

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    // Declare the view binding variable
    protected lateinit var binding: Binding
    private var progressDialog: AppCompatDialog? = null

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

    protected fun showProgressDialog() {
        progressDialog = AppCompatDialog(this)
        progressDialog?.setTitle(resources.getString(R.string.loading)) // Set your loading message
        progressDialog?.setCancelable(true)
        progressDialog?.show()
    }

    protected fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}