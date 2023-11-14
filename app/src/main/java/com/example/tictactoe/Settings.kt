package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.example.tictactoe.base.BaseActivity
import com.example.tictactoe.databinding.ActivitySettingsBinding
import com.example.tictactoe.viewmodel.SettingsViewModelImpl
import org.koin.android.ext.android.inject

class Settings : BaseActivity<ActivitySettingsBinding>() {
    private val settingsViewModel: SettingsViewModelImpl by inject()
    companion object{
        fun newIntent(context:Context){
            val intent = Intent(context, Settings::class.java)
            context.startActivity(intent)
        }
    }
    override fun createViewBinding(): ActivitySettingsBinding {
        return ActivitySettingsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSettingTheme()
        getAnimationSetting()
        getSoundSetting()
    }
    private fun getAnimationSetting(){
        val animationIsOn = settingsViewModel.isAnimationOn()

        binding.animationAction.isChecked = animationIsOn
        binding.animationAction.setOnCheckedChangeListener { compoundButton, b ->
            settingsViewModel.setAnimation(b)
        }
    }
    private fun getSoundSetting(){
        val soundIsOn = settingsViewModel.isSoundEnable()

        binding.soundAction.isChecked = soundIsOn
        binding.soundAction.setOnCheckedChangeListener { compoundButton, b ->
            settingsViewModel.setSoundEnable(b)
        }
    }
    private fun getSettingTheme(){
        val themeIsLight = settingsViewModel.isLightTheme

        themeIsLight?.let { isLightTheme ->
            setRadioButtonChecked(isLightTheme)
            setAppTheme(isLightTheme)
        } ?: run {
            val isNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            setRadioButtonChecked(!isNightMode)
            settingsViewModel.setModeTheme(!isNightMode)
        }

        binding.btnTheme.setOnCheckedChangeListener { _, checkedId ->
            val isLightTheme = checkedId == R.id.btnLight
            setAppTheme(isLightTheme)
            settingsViewModel.setModeTheme(isLightTheme)
        }
    }
    private fun setRadioButtonChecked(isLightTheme: Boolean) {
        binding.btnTheme.check(if (isLightTheme) R.id.btnLight else R.id.btnDark)
    }
    private fun setAppTheme(isLightTheme: Boolean) {
        val nightMode = if (isLightTheme) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(nightMode)
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