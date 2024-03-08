package com.fikrielg.quranpocket.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences

object GlobalState {
    var ayahTextSize by mutableFloatStateOf(SettingPreferences.ayahTextSize)
    var isFocusRead by mutableStateOf(SettingPreferences.isFocusReadActive)
    var isOnBoarding by mutableStateOf(SettingPreferences.isOnBoarding)
}