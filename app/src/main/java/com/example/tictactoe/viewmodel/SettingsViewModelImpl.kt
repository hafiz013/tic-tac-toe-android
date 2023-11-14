package com.example.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tictactoe.core.SharedPreferenceManager

class SettingsViewModelImpl(private val sharedPreferenceManager: SharedPreferenceManager): ViewModel(), SettingsViewModel {
    private val _isLightTheme: Boolean? = sharedPreferenceManager.getThemeIsLight()
    override val isLightTheme: Boolean?
        get() = _isLightTheme
    override fun setModeTheme(themeIsLight: Boolean) {
        sharedPreferenceManager.setTheme(themeIsLight)
    }
    override fun setAnimation(isAnimationOn: Boolean) {
       sharedPreferenceManager.setAnimation(isAnimationOn)
    }
    override fun setSoundEnable(isSoundEnable: Boolean) {
       sharedPreferenceManager.setSound(isSoundEnable)
    }
    override fun isAnimationOn(): Boolean {
        return sharedPreferenceManager.getAnimation()
    }
    override fun isSoundEnable(): Boolean {
        return sharedPreferenceManager.getSound()
    }


}