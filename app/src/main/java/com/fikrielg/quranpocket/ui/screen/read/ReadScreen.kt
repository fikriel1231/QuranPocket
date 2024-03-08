package com.fikrielg.quranpocket.ui.screen.read

import android.content.Intent
import android.util.LayoutDirection
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.kotpref.LastReadPreferences
import com.fikrielg.quranpocket.ui.component.AyahItem
import com.fikrielg.quranpocket.ui.component.BottomSheetItem
import com.fikrielg.quranpocket.ui.component.FloatingPlaybackBar
import com.fikrielg.quranpocket.ui.component.QuranPocketAppBar
import com.fikrielg.quranpocket.ui.component.TopBarActionItem
import com.fikrielg.quranpocket.ui.screen.GlobalViewModel

import com.fikrielg.quranpocket.ui.theme.montserrat
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Destination(navArgsDelegate = ReadArguments::class)
fun ReadScreen(
    viewModel: ReadViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    globalViewModel: GlobalViewModel
) {

    val ayahList by viewModel.ayahList.collectAsStateWithLifecycle()
    val surahName by viewModel.currentRead
    val playerQuran = viewModel.quran.value

    val context = LocalContext.current

    val readListState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val snackbarHostState = SnackbarHostState()

    val isPlaying by viewModel.isPlaying



    LaunchedEffect(true) {
        delay(300)
        readListState.animateScrollToItem(viewModel.lastPosition ?: 0)
    }

    val totalAyah = remember {
        globalViewModel.getTotalAyah()
    }

    viewModel.uiEventFlow.collectAsState(initial = ReadUiEvent.Idle).let { state ->
        when (val event = state.value) {
            is ReadUiEvent.Idle -> {}
            is ReadUiEvent.SuccessAddToBookmark -> {
                scope.launch { snackbarHostState.showSnackbar(event.message) }
            }

            is ReadUiEvent.SuccessCopiedAyah -> {
                scope.launch { snackbarHostState.showSnackbar(event.message) }
            }

            is ReadUiEvent.SuccessSharedAyah -> {
                scope.launch { snackbarHostState.showSnackbar(event.message) }
            }

            is ReadUiEvent.ErrorPlayingAyah -> {
                scope.launch { snackbarHostState.showSnackbar(event.message) }
            }

            is ReadUiEvent.PlayingAyahChanged -> {
                scope.launch { readListState.animateScrollToItem(event.position) }
            }
        }
    }


            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

                topBar = {
                    QuranPocketAppBar(
                        currentDestinationTitle = surahName,
                        navigateUp = { navigator.popBackStack()},
                    )

                },

                bottomBar = {
                    if (isPlaying != PlayStatus.NOT_PLAYING) {
                        FloatingPlaybackBar(
                            surahName = playerQuran?.surahNameEn,
                            ayahNumber = playerQuran?.ayahNumber.toString(),
                            qoriName = "",
                            qoriImage = "",
                            onNextClicked = {
                                viewModel.onPlayAyahEvent(PlayAyahEvent.SkipNext)
                            },
                            onPreviousClicked = {
                                viewModel.onPlayAyahEvent(PlayAyahEvent.SkipPrevious)
                            },
                            onPlayPauseClicked = {
                                viewModel.onPlayAyahEvent(PlayAyahEvent.PlayPauseAyah)
                            },
                            isPaused = isPlaying,
                            onStopClicked = {
                                viewModel.onPlayAyahEvent(PlayAyahEvent.StopAyah)
                            }
                        )
                    }
                },

                ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                ) {

                    LazyColumn(
                        state = readListState,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(
                            bottom = 24.dp
                        ),
                    ) {
                        items(ayahList.size) { index ->
                            val quran = ayahList[index]

                            with(LastReadPreferences) {
                                lastSurahName = quran.surahNameEn
                                lastPosition = index
                                lastSurahNumber = quran.surahNumber!!
                            }


                            if (showBottomSheet) {
                                ModalBottomSheet(
                                    onDismissRequest = {
                                        showBottomSheet = false
                                    },
                                    sheetState = sheetState
                                ) {
                                    Column {
                                        BottomSheetItem(
                                            title = "Play Ayah",
                                            resourceId = R.drawable.baseline_play_circle_outline_24,
                                            onClick = {
                                                showBottomSheet = false
                                                viewModel.onEvent(
                                                    ReadEvent.PlayAyah(
                                                        quran = quran
                                                    )
                                                )
                                            })
                                        BottomSheetItem(
                                            title = "Bookmark",
                                            resourceId = R.drawable.round_bookmark_24,
                                            onClick = {
                                                viewModel.onEvent(
                                                    ReadEvent.SaveBookmark(
                                                        surahName = quran.surahNameEn!!,
                                                        ayahText = quran.ayahText!!,
                                                        context = context,
                                                        position = index,
                                                        translationId = quran.translation_id!!,
                                                        ayahNumber = quran.ayahNumber!!,
                                                        surahNumber = quran.surahNumber!!,
                                                    )
                                                )
                                                showBottomSheet = false
                                            })
                                        BottomSheetItem(
                                            title = "Copy",
                                            resourceId = R.drawable.round_content_copy_24,
                                            onClick = {
                                                viewModel.onEvent(
                                                    ReadEvent.CopyAyah(
                                                        context = context,
                                                        surahName = quran.surahNameEn!!,
                                                        ayahText = quran.ayahText!!,
                                                        translation = quran.translation_id!!
                                                    )
                                                )
                                                showBottomSheet = false
                                            })
                                        BottomSheetItem(
                                            title = "Share",
                                            resourceId = R.drawable.round_share_24,
                                            onClick = {
                                                viewModel.onEvent(
                                                    ReadEvent.ShareAyah(
                                                        context = context,
                                                        surahName = quran.surahNameEn!!,
                                                        ayahText = quran.ayahText!!,
                                                        translation = quran.translation_id!!
                                                    )
                                                )
                                                showBottomSheet = false
                                            })
                                        Spacer(modifier = Modifier.height(40.dp))
                                    }
                                }
                            }


                            AyahItem(
                                quran = quran,
                                totalAyah = totalAyah[quran.surahNumber!! - 1],
                                onClick = {
                                    showBottomSheet = true
                                },
                                onPlayAll = {
                                    viewModel.onEvent(ReadEvent.PlayAllAyah(ayahList))
                                }
                            )
                        }
                    }
                }
            }
        }


data class ReadArguments(
    val readType: Int = 0,
    val surahNumber: Int? = null,
    val pageNumber: Int? = null,
    val juzNumber: Int? = null,
    val position: Int? = null
)