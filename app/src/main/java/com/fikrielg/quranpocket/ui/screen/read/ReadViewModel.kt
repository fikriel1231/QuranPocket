package com.fikrielg.quranpocket.ui.screen.read

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.ui.screen.navArgs
import com.fikrielg.quranpocket.utils.Converters
import com.fikrielg.quranpocket.utils.GlobalActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val repository: QuranRepository,
    savedStateHandle: SavedStateHandle, //biar msk lngsng ke viewmodelnya
    private val playerClient: PlayerClient
) : ViewModel() {
    private val navArgs: ReadArguments = savedStateHandle.navArgs()

    private val _ayahList = MutableStateFlow<List<Quran>>(emptyList())
    val ayahList = _ayahList.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentRead = mutableStateOf("")
    val currentRead = _currentRead

    val lastPosition = navArgs.position

    private val _uiEventFlow = MutableSharedFlow<ReadUiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    val playerType = mutableStateOf(PlayType.NONE)
    val isPlaying = mutableStateOf<PlayStatus>(PlayStatus.NOT_PLAYING)
    val playMode = mutableStateOf(PlayMode.SINGLE_ONCE)

    val quran = mutableStateOf<Quran?>(null)

    private val _currentPlayedAyah = MutableStateFlow("")
    val currentPlayedAyah = _currentPlayedAyah.asStateFlow()

//    private val _currentReadingState = mutableStateOf("")
//    val currentReadingState: State<String> = _currentReadingState

    fun load() {

        when (navArgs.readType) {
            0 -> { //Surat
                repository.readAyahBySurah(navArgs.surahNumber ?: 0).onEach {
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
                    viewModelScope.launch { _uiEventFlow.emit(ReadUiEvent.SuccessCopiedAyah("Ayah Copied")) }
                }

                is ReadEvent.SaveBookmark -> {
                    viewModelScope.launch {
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
                        viewModelScope.launch {
                            _uiEventFlow.emit(
                                ReadUiEvent.SuccessAddToBookmark("Berhasil menyimpan bookmark")
                            )
                        }
                    }
                }

                is ReadEvent.ShareAyah -> {
                    GlobalActions.shareAyah(
                        context = event.context,
                        ayahText = event.ayahText,
                        surahName = event.surahName,
                        translation = event.translation
                    )
                    viewModelScope.launch { _uiEventFlow.emit(ReadUiEvent.SuccessSharedAyah("Ayat dibagikan")) }
                }

                is ReadEvent.PlayAllAyah -> {
                    playerClient.stop()
                    val musicItems = mutableListOf<MusicItem>()
                    event.qoranList.forEach { quran ->
                        val musicItem = createMusicItem(
                            surahName = quran.surahNameEn ?: return@forEach,
                            surahNumber = quran.surahNumber ?: return@forEach,
                            ayahNumber = quran.ayahNumber ?: return@forEach
                        )
                        musicItems.add(musicItem)
                    }
                    val playlist = Playlist.Builder().appendAll(musicItems).build()
                    playerClient.connect {
                        playerClient.setPlaylist(playlist, true)
                        playerClient.playMode = PlayMode.LOOP
                        playMode.value = PlayMode.LOOP
                        playerType.value = PlayType.PLAY_ALL
                        isPlaying.value = PlayStatus.IS_PLAYING
                        playerClient.addOnPlayingMusicItemChangeListener { _, position, _ ->
                            quran.value = event.qoranList[position]
                            viewModelScope.launch {
                                _uiEventFlow.emit(
                                    ReadUiEvent.PlayingAyahChanged(
                                        position
                                    )
                                )
                                if (playerClient.isError) {
                                    _uiEventFlow.emit(ReadUiEvent.ErrorPlayingAyah(playerClient.errorMessage))
                                }
                            }
                        }
                    }
                }

                is ReadEvent.PlayAyah -> {
                    playerClient.stop()
                    val musicItem = createMusicItem(
                        event.quran.surahNameEn, event.quran.surahNumber, event.quran.ayahNumber
                    )
                    val playlist = Playlist.Builder().append(musicItem).build()
                    playerClient.connect {
                        playerClient.setPlaylist(playlist, true)
                        isPlaying.value = PlayStatus.IS_PLAYING
                        playerType.value = PlayType.PLAY_SINGLE
                        quran.value = event.quran
                    }
                }

            }
        }
    }

    fun onPlayAyahEvent(event: PlayAyahEvent) {
        when (event) {
            is PlayAyahEvent.PauseAyah -> playerClient.pause()
            is PlayAyahEvent.PlayPauseAyah -> {
                playerClient.playPause()
                isPlaying.value = if (isPlaying.value == PlayStatus.IS_PAUSED) {
                    PlayStatus.IS_PLAYING
                } else {
                    PlayStatus.IS_PAUSED
                }
            }

            is PlayAyahEvent.SkipNext -> playerClient.skipToNext()
            is PlayAyahEvent.SkipPrevious -> playerClient.skipToPrevious()
            is PlayAyahEvent.StopAyah -> {
                playerClient.stop()
                isPlaying.value = PlayStatus.NOT_PLAYING
                playerType.value = PlayType.NONE
                playerClient.shutdown()
            }

            PlayAyahEvent.ChangePlayerMode -> {
                if (playerClient.playMode == PlayMode.PLAYLIST_LOOP) {
                    playerClient.playMode = PlayMode.SINGLE_ONCE
                    playMode.value = PlayMode.SINGLE_ONCE
                } else {
                    playerClient.playMode = PlayMode.PLAYLIST_LOOP
                    playMode.value = PlayMode.PLAYLIST_LOOP
                }
            }
        }
    }

    private fun createMusicItem(
        surahName: String?,
        surahNumber: Int?,
        ayahNumber: Int?
    ): MusicItem {

        val formattedSurahNumber = Converters.convertNumberToThreeDigits(surahNumber ?: 0)
        val formattedAyahNumber = Converters.convertNumberToThreeDigits(ayahNumber ?: 0)

        return MusicItem.Builder()
            .setTitle("QS $surahName : $ayahNumber")
            .setArtist("Abdul Samad")
            .setUri("https://everyayah.com/data/${SettingPreferences.currentQori.qoriId}/$formattedSurahNumber$formattedAyahNumber.mp3")
            .setIconUri(SettingPreferences.currentQori.qoriImage)
            .autoDuration()
            .build()
    }


    init {
        load()
    }
}


enum class PlayType {
    NONE,
    PLAY_ALL,
    PLAY_SINGLE
}

enum class PlayStatus {
    IS_PLAYING,
    NOT_PLAYING,
    IS_PAUSED
}

sealed class PlayAyahEvent {
    object SkipPrevious : PlayAyahEvent()
    object PlayPauseAyah : PlayAyahEvent()
    object PauseAyah : PlayAyahEvent()
    object SkipNext : PlayAyahEvent()
    object StopAyah : PlayAyahEvent()
    object ChangePlayerMode : PlayAyahEvent()
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

    data class PlayAyah(
        val quran: Quran,
    ) : ReadEvent()

    data class PlayAllAyah(
        val qoranList: List<Quran>
    ) : ReadEvent()

}


sealed class ReadUiEvent {
    object Idle : ReadUiEvent()
    data class SuccessAddToBookmark(val message: String) : ReadUiEvent()
    data class SuccessCopiedAyah(val message: String) : ReadUiEvent()
    data class SuccessSharedAyah(val message: String) : ReadUiEvent()
    data class PlayingAyahChanged(val position: Int) : ReadUiEvent()
    data class ErrorPlayingAyah(val message: String) : ReadUiEvent()
}