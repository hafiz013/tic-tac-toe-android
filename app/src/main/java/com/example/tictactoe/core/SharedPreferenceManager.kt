package com.example.tictactoe.core

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(private val context: Context) {
    private val PREF_KEY = "PREFERENCE_SETTINGS"
    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    private val THEME_IS_LIGHT = "LIGHT"
    private val ANIMATION = "ANIMATION"
    private val SOUND_ENABLE = "SOUND"
    private val editor = prefs.edit()

    fun getThemeIsLight():Boolean?{
        return if(prefs.contains(THEME_IS_LIGHT)){
            return prefs.getBoolean(THEME_IS_LIGHT, false)
        }else{
            null
        }
    }
    fun setTheme(theme:Boolean){
        editor.putBoolean(THEME_IS_LIGHT, theme)
        editor.apply()
    }

    fun getAnimation():Boolean{
        return prefs.getBoolean(ANIMATION, true)
    }
    fun setAnimation(isAnimationOn:Boolean){
        editor.putBoolean(ANIMATION, isAnimationOn)
        editor.apply()
    }
    fun getSound():Boolean{
       return  prefs.getBoolean(SOUND_ENABLE, true)
    }
    fun setSound(isSoundEnable:Boolean){
        editor.putBoolean(SOUND_ENABLE, isSoundEnable)
        editor.apply()
    }
}