package com.fikrielg.quranpocket.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import com.fikrielg.quranpocket.ui.component.AyahSearchItem
import com.fikrielg.quranpocket.ui.component.SurahItem
import com.fikrielg.quranpocket.ui.screen.destinations.ReadScreenDestination
import com.fikrielg.quranpocket.ui.screen.read.ReadArguments
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,

    ) {

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchSurahList by viewModel.searchSurahState.collectAsState()
    val searchAyahList by viewModel.searchAyahState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    viewModel.searchResult(searchText)


    Scaffold(
    ) {
        Column(modifier = Modifier.padding(it)) {
            SearchBar(
                query = searchText,
                onQueryChange = {
                    viewModel.onSearchTextChange(it)
                },
                onSearch = {
                    keyboardController?.hide()
                },
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchTextChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                },
                placeholder = { Text(text = "Cari surat atau ayat") },
                active = isSearching,
                onActiveChange = { viewModel.onToogleSearch() },
                modifier = Modifier
                    .onFocusChanged { it }
                    .fillMaxWidth(),
                tonalElevation = 0.dp
            ) {
                if (searchAyahList.isNotEmpty() || searchSurahList.isNotEmpty()) {
                    LazyColumn(content = {
                        items(searchSurahList + searchAyahList) { item ->
                            when (item) {
                                is Surah -> SurahItem(
                                    surah = item,
                                    onClick = {
                                        navigator.navigate(
                                            ReadScreenDestination(
                                                ReadArguments(
                                                    readType = 0,
                                                    surahNumber = item.surahNumber,
                                                )
                                            )
                                        )
                                    }

                                )

                                is Quran -> AyahSearchItem(
                                    quran = item,
                                    onClick = {
                                        navigator.navigate(
                                            ReadScreenDestination(
                                                ReadArguments(
                                                    readType = 0,
                                                    surahNumber = item.surahNumber,
                                                    position = item.ayahNumber?.minus(1)
                                                )
                                            )
                                        )
                                    },
                                    searchText = viewModel.searchText.value
                                )
                            }
                        }
                    }
                    )
                } else {
                    SearchEmptyState()
                }
            }
        }
    }
}

@Composable
fun SearchEmptyState(
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Tidak menemukan surat atau ayat yang dicari",
            fontFamily = montserrat,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Coba ketik lagi yang kamu cari",
            fontFamily = montserrat
        )
    }
}