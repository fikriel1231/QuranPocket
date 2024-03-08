package com.fikrielg.quranpocket.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.kotpref.LastReadPreferences
import com.fikrielg.quranpocket.data.source.local.entities.Juz
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import com.fikrielg.quranpocket.ui.component.AyahItem
import com.fikrielg.quranpocket.ui.component.AyahSearchItem
import com.fikrielg.quranpocket.ui.component.JuzItem
import com.fikrielg.quranpocket.ui.component.PageItem
import com.fikrielg.quranpocket.ui.component.QuranPocketAppBar
import com.fikrielg.quranpocket.ui.component.SurahItem
import com.fikrielg.quranpocket.ui.component.customGradient
import com.fikrielg.quranpocket.ui.screen.GlobalViewModel
import com.fikrielg.quranpocket.ui.screen.bookmark.BookmarkScreen
import com.fikrielg.quranpocket.ui.screen.bookmark.BookmarkViewModel
import com.fikrielg.quranpocket.ui.screen.destinations.BookmarkScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.PrayerTimeScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.QiblaScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.ReadScreenDestination
import com.fikrielg.quranpocket.ui.screen.onboarding.OnBoardingScreen
import com.fikrielg.quranpocket.ui.screen.read.ReadArguments
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.utils.GlobalState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Box as Box


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination(start = true)
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    globalViewModel: GlobalViewModel,
    viewModelBook: BookmarkViewModel = hiltViewModel(),
) {


    val surahList = viewModel.surahListState.value
    val juzList = viewModel.juzListState.value
    val pageList = viewModel.pageListState.value

    val bookmarkList = viewModelBook.bookmarkState.collectAsStateWithLifecycle().value


    val context = LocalContext.current

    LaunchedEffect(surahList) {
        globalViewModel.setTotalAyah(surahList = surahList)
    }

        Scaffold(
            topBar = {
                QuranPocketAppBar(
                    currentDestinationTitle = "Quran Pocket",
                    navigateUp = { /*TODO*/ },
                    isHomeScreen = true
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            ) {

                Row() {
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(22.dp))
                            .fillMaxWidth()
                            .weight(1f)
                            .height(140.dp)
                            .background(
                                color = Color(0xFF7986CB)
                            )
                            .clickable {
                                if (LastReadPreferences.lastSurahName != null)
                                    navigator.navigate(
                                        ReadScreenDestination(
                                            ReadArguments(
                                                readType = 0,
                                                surahNumber = LastReadPreferences.lastSurahNumber,
                                                position = LastReadPreferences.lastPosition
                                            )
                                        )
                                    )
                            },
                        contentAlignment = Alignment.Center

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.book_open),
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column() {
                                Text(
                                    text = LastReadPreferences.lastSurahName
                                        ?: "Belum membaca apapun",
                                    fontFamily = montserrat,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (LastReadPreferences.lastSurahNumber != 0)
                                    Text(
                                        text = "Ayat ${LastReadPreferences.lastSurahNumber}",
                                        fontFamily = montserrat,
                                        color = Color.White,
                                        fontSize = 13.sp
                                    )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .weight(1F)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(22.dp))
                                .fillMaxWidth()
                                .height(65.dp)
                                .background(
                                    color = Color(0xFF7986CB)
                                )
                                .clickable {
                                    navigator.navigate(
                                        PrayerTimeScreenDestination
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.prayertime),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Jadwal Sholat",
                                    fontFamily = montserrat,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(22.dp))
                                .fillMaxWidth()
                                .height(65.dp)
                                .background(
                                    color = Color(0xFF7986CB)
                                )
                                .clickable {
                                    navigator.navigate(
                                        QiblaScreenDestination
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.qibla),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Cari Qiblat",
                                    fontFamily = montserrat,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                val tabItems = stringArrayResource(id = R.array.txt_tab_items)

                val scope = rememberCoroutineScope()
                val pagerState = rememberPagerState(pageCount = { tabItems.size })

                val surahScrollState = rememberLazyListState()
                val juzScrollState = rememberLazyListState()
                val pageScrollState = rememberLazyListState()

                var visibleDropdownJuz by remember {
                    mutableStateOf(true)
                }

                var selectedJuz by remember { mutableStateOf(1) }

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                ) {
                    tabItems.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = item,
                                    fontFamily = montserrat,
                                    fontWeight = if (pagerState.currentPage == index) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                ) { index ->
                    when (index) {
                        ORDER_BY_SURAH -> {
                            LazyColumn(
                                contentPadding = PaddingValues(
                                    vertical = 10.dp
                                ),
                                state = surahScrollState,
                            ) {
                                items(surahList) { surah ->
                                    SurahItem(
                                        surah = surah,
                                        onClick = {
                                            navigator.navigate(
                                                ReadScreenDestination(
                                                    ReadArguments(
                                                        readType = pagerState.currentPage,
                                                        surahNumber = surah.surahNumber,
                                                    )
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        ORDER_BY_JUZ -> LazyColumn(
                            state = juzScrollState,
                        ) {
                            itemsIndexed(juzList) { index, juz ->
                                val nextPage =
                                    if (index < juzList.size - 1) juzList[index + 1] else null

                                JuzItem(juz = juz, nextPage = nextPage, onClick = {
                                    navigator.navigate(
                                        ReadScreenDestination(
                                            ReadArguments(
                                                readType = pagerState.currentPage,
                                                juzNumber = juz.juzNumber,
                                            )
                                        )
                                    )
                                })
                            }
                        }

                        BOOKMARK -> BookmarkScreen(navigator = navigator)

                    }
                }

            }
        }
    }




data class JuzItem(
    val juzNumber: Int,
    val listJuz: List<Juz>
)


const val ORDER_BY_SURAH = 0
const val ORDER_BY_JUZ = 1
const val BOOKMARK = 2

