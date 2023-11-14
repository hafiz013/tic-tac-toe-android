package com.example.tictactoe.viewmodel

interface SettingsViewModel {
    val isLightTheme:Boolean?
    fun setModeTheme(themeIsLight:Boolean)
    fun setAnimation(isAnimationOn:Boolean)
    fun setSoundEnable(isSoundEnable:Boolean)
    fun isAnimationOn():Boolean
    fun isSoundEnable():Boolean
}