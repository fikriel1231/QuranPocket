package com.fikrielg.quranpocket.ui.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import com.fikrielg.quranpocket.data.source.local.entities.Juz
import com.fikrielg.quranpocket.data.source.local.entities.Page
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QuranRepository
) : ViewModel() {
    private val _surahListState = mutableStateOf(emptyList<Surah>())
    val surahListState = _surahListState

    private val _juzListState = mutableStateOf(emptyList<Juz>())
    val juzListState = _juzListState

    private val _pageListState = mutableStateOf(emptyList<Page>())
    val pageListState = _pageListState


    private var getSurahJob: Job? = null
    private var getJuzJob: Job? = null //agar keload bersamaan
    private var getPageJob: Job? = null

    private fun getAllQuranIndex() {
        getSurahJob?.cancel()
        getJuzJob?.cancel()
        getPageJob?.cancel()

        getSurahJob = repository.showQuranBySurah().onEach { list -> _surahListState.value = list }
            .launchIn(viewModelScope)
        getJuzJob = repository.showQuranByJuz().onEach { list -> _juzListState.value = list }
            .launchIn(viewModelScope)
        getPageJob = repository.showQuranByPage().onEach { list -> _pageListState.value = list }
            .launchIn(viewModelScope)

    }

    init {
        getAllQuranIndex()
    }
}