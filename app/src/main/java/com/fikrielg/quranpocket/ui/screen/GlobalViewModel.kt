package com.fikrielg.quranpocket.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.fikrielg.quranpocket.data.source.local.entities.Surah

class GlobalViewModel : ViewModel() {
    private val _totalAyah = mutableStateListOf<Int>()

    fun setTotalAyah(surahList: List<Surah>) {
        surahList.forEach {
            _totalAyah.add(it.ayahTotal ?: 0)
        }
    }

    fun getTotalAyah() = _totalAyah
}

