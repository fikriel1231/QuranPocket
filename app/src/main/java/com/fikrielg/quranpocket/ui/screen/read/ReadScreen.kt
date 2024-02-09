package com.fikrielg.quranpocket.ui.screen.read

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
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

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val readListState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        delay(300)
        readListState.animateScrollToItem(viewModel.lastPosition ?: 0)
    }

    val totalAyah = remember {
        globalViewModel.getTotalAyah()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = surahName,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
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
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "QS ${quran.surahNameEn} : ${quran.ayahNumber}\n\n${quran.ayahText}\n${quran.translation_id}"
                        )
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)


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
                                        clipboardManager.setText(
                                            AnnotatedString(
                                                "QS ${quran.surahNameEn} : ${quran.ayahNumber}\n\n${quran.ayahText}\n${quran.translation_id}"
                                            )
                                        )
                                        showBottomSheet = false
                                        Toast.makeText(
                                            context,
                                            "Copied to clipboard",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    })
                                BottomSheetItem(
                                    title = "Share",
                                    resourceId = R.drawable.round_share_24,
                                    onClick = {
                                        showBottomSheet = false
                                        context.startActivity(sendIntent)
                                    })
                                Spacer(modifier = Modifier.height(40.dp))
                            }
                        }
                    }


                    AyahItem(
                        ayahNumber = quran.ayahNumber,
                        ayahTextAr = quran.ayahText,
                        ayahTextId = quran.translation_id,
                        surahName = quran.surahNameEn,
                        surahNameId = quran.surahNameId,
                        totalAyah = totalAyah[quran.surahNumber!! - 1],
                        onClick = {
                            showBottomSheet = true
                        }

                    )

                    with(LastReadPreferences) {
                        lastSurahName = quran.surahNameEn
                        lastPosition = index
                        lastSurahNumber = quran.surahNumber
                    }
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