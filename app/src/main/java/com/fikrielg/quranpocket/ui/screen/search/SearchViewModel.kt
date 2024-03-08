package com.fikrielg.quranpocket.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: QuranRepository) : ViewModel() {
    private val _searchSurahState = MutableStateFlow(emptyList<Surah>())
    val searchSurahState = _searchSurahState.asStateFlow()

    private val _searchAyahState = MutableStateFlow(emptyList<Quran>())
    val searchAyahState = _searchAyahState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun searchResult(text: String) {
        repository.searchSurah(text).onEach { listQuran ->
            _searchSurahState.emit(listQuran)
        }.launchIn(viewModelScope)

        repository.searchEntireSurah(text).onEach { listAyah ->
            _searchAyahState.emit(listAyah)
        }.launchIn(viewModelScope)
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }
}