package com.fikrielg.quranpocket.ui.screen.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.ui.component.BookmarkItem
import com.fikrielg.quranpocket.ui.component.CustomAlertDialog
import com.fikrielg.quranpocket.ui.component.QuranPocketAppBar
import com.fikrielg.quranpocket.ui.screen.destinations.ReadScreenDestination
import com.fikrielg.quranpocket.ui.screen.read.ReadArguments
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookmarkScreen(
    navigator: DestinationsNavigator,
    viewModel: BookmarkViewModel = hiltViewModel(),
) {
    val bookmarkList = viewModel.bookmarkState.collectAsStateWithLifecycle().value

    var showAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            QuranPocketAppBar(
                currentDestinationTitle = "",
                navigateUp = { /*TODO*/ },
                isHomeScreen = true,
                actions = {
                    if (bookmarkList.isNotEmpty())
                        IconButton(onClick = {
                            showAlertDialog = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_delete_sweep_24),
                                contentDescription = "Delete All Bookmark",
                            )
                        }
                })
        }
    ) {
        if (showAlertDialog) {
            CustomAlertDialog(
                onDismiss = {
                    showAlertDialog = false
                },
                onExit = {
                    showAlertDialog = false
                    viewModel.onEvent(BookScreenEvent.DeleteAllBookmark)
                },
                title = "Hapus semua bookmark",
                message = "Apakah anda yakin menghapus semua bookmark?",
                dismissText = "Hapus",
                exitText = "Batal"
            )

        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        ) {
            if (bookmarkList.isNotEmpty())
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(bookmarkList) { bookmark ->
                        BookmarkItem(
                            bookmark = bookmark,
                            onClickDelete = {
                                viewModel.onEvent(BookScreenEvent.DeleteBookmark(bookmark))
                            }) { surahNumber, position ->
                            navigator.navigate(
                                ReadScreenDestination(
                                    ReadArguments(
                                        readType = 0,
                                        surahNumber = surahNumber,
                                        position = position
                                    )
                                )
                            )
                        }
                    }
                }
            else
                Text(text = "Anda belum menambahkan ayat apapun")
        }
    }


}