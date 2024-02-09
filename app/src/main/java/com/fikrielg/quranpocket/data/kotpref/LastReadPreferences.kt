package com.fikrielg.quranpocket.data.kotpref

import com.chibatching.kotpref.KotprefModel
import com.fikrielg.quranpocket.ui.screen.home.ORDER_BY_SURAH

object LastReadPreferences: KotprefModel() {
    var lastSurahName by nullableStringPref(null)
    var lastSurahNumber by intPref(0)
    var lastPosition by intPref(0)
}