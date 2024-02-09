package com.fikrielg.quranpocket.ui.screen.read

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.ui.screen.navArgs
import com.fikrielg.quranpocket.utils.GlobalActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val repository: QuranRepository,
    savedStateHandle: SavedStateHandle, //biar msk lngsng ke viewmodelnya
) : ViewModel() {
    val navArgs: ReadArguments = savedStateHandle.navArgs()

    private val _ayahList = MutableStateFlow<List<Quran>>(emptyList())
    val ayahList = _ayahList.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentRead = mutableStateOf("")
    val currentRead = _currentRead

    val lastPosition = navArgs.position

    private val _currentReadingState = mutableStateOf("")
    val currentReadingState: State<String> = _currentReadingState

    fun load() {

        when (navArgs.readType) {
            0 -> { //Surat
                repository.readAyahBySurah(navArgs.surahNumber ?: 0).onEach {
                    Log.d("KONTCI", navArgs.surahNumber.toString())
                    _ayahList.emit(it)
                    _currentRead.value = it[0].surahNameEn.toString()
                }.launchIn(viewModelScope)
            }

            1 -> { //Juz
                repository.readAyahByJuz(navArgs.juzNumber ?: 0).onEach {
                    _ayahList.emit(it)
                    _currentRead.value = it[0].juzNumber.toString()
                }.launchIn(viewModelScope)
            }

            2 -> { //Page
                repository.readAyahByPage(navArgs.pageNumber ?: 0).onEach {
                    _ayahList.emit(it)
                    _currentRead.value = "Halaman ${it[0].page}"
                }.launchIn(viewModelScope)
            }

            else -> {}
        }
    }

    fun onEvent(event: ReadEvent) {
        viewModelScope.launch {
            when (event) {
                is ReadEvent.CopyAyah -> {
                    GlobalActions.copyAyah(
                        event.context,
                        event.surahName,
                        event.ayahText,
                        event.translation
                    )
                }
                is ReadEvent.SaveBookmark -> {
                    repository.insertBookmark(
                        Bookmark(
                            id = null,
                            surahNumber = event.surahNumber,
                            surahName = event.surahName,
                            ayahNumber = event.ayahNumber,
                            ayahText = event.ayahText,
                            translationId = event.translationId,
                            position = event.position,
                        )
                    )
                }
                is ReadEvent.SetCurrentReading -> {
                    _currentReadingState.value = event.currentReading
                }
                is ReadEvent.ShareAyah -> {
                    GlobalActions.shareAyah(
                        context = event.context,
                        ayahText = event.ayahText,
                        surahName = event.surahName,
                        translation = event.translation
                    )
                }
            }
        }
    }


    init {
        load()
    }
}

sealed class ReadEvent {
    data class CopyAyah(
        val context: Context,
        val surahName: String,
        val ayahText: String,
        val translation: String
    ) : ReadEvent()

    data class ShareAyah(
        val context: Context,
        val surahName: String,
        val ayahText: String,
        val translation: String
    ) : ReadEvent()

    data class SaveBookmark(
        val context: Context,
        val surahName: String,
        val surahNumber: Int,
        val ayahNumber: Int,
        val ayahText: String,
        val translationId: String,
        val position: Int,
    ) : ReadEvent()

    data class SetCurrentReading(val currentReading: String) : ReadEvent()
}