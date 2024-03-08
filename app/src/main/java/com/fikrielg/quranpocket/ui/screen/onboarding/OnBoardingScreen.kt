package com.fikrielg.quranpocket.ui.screen.onboarding

import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences
import com.fikrielg.quranpocket.utils.GlobalState


import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.fikrielg.quranpocket.ui.screen.destinations.Destination
import com.fikrielg.quranpocket.ui.screen.destinations.HomeScreenDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun OnBoardingScreen(
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current

    BackHandler {
        Toast.makeText(context, "Please finish the app introduction first", Toast.LENGTH_SHORT)
            .show()
    }

    val requiredLocation = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val permissionState = rememberMultiplePermissionsState(permissions = requiredLocation)

    val pagerState = rememberPagerState { 4 }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 1) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(128.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1F),
                ) {
                    if (pagerState.currentPage > 0) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        ) {
                            Text(text = "Previous")
                        }
                    }
                }
                Row(
                    modifier = Modifier.weight(2F),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (0..3).forEach { i ->
                        Icon(
                            painterResource(id = R.drawable.baseline_circle_24),
                            contentDescription = "Step $i",
                            tint = if (pagerState.currentPage == i) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
                TextButton(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < 3) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                return@launch
                            }
                            if (pagerState.currentPage == 3) {
                                navigator.navigate(HomeScreenDestination())
                                SettingPreferences.isOnBoarding = false
                                GlobalState.isOnBoarding = false
                            }
                        }
                    }
                ) {
                    Text(
                        text = if (pagerState.currentPage < 3) "Next" else "Finish"
                    )
                }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { pageNumber ->
                when (pageNumber) {
                    0 -> OnBoardingContentWelcome()
                    1 -> OnBoardingContentLocation()
                    2 -> OnBoardingContentLanguage()
                    3 -> OnBoardingContentComplete()
                }
            }
        }
    }
}

@Composable
private fun OnBoardingContentWelcome(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome to Quran Pocket",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(14.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Quran Pocket Logo"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Press \\\"Next\\\" to start",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun OnBoardingContentLocation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Allow Location Access",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_location_on_24),
            contentDescription = "Location Graphics"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "The app will get adzan schedule based on your location now (You can turn the permission on/off later on settings)",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun OnBoardingContentLanguage(
    modifier: Modifier = Modifier
) {

    var isDialogShowed by remember {
        mutableStateOf(false)
    }
    var languageNameState by remember {
        mutableStateOf(
            SettingPreferences.Language.EN
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Change Language",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Icon(
            painterResource(id = R.drawable.round_language_24),
            contentDescription = "Language Graphics",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .size(128.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Quran Pocket provides Qoran translation in 2 languages, please choose the one you comfort with",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box {
            TextButton(
                onClick = {
                    isDialogShowed = true
                }
            ) {
                Icon(painterResource(id = R.drawable.round_language_24), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (SettingPreferences.currentLanguage == SettingPreferences.Language.EN) "English"
                    else "Indonesia"
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Language"
                )
            }
            DropdownMenu(
                expanded = isDialogShowed,
                onDismissRequest = { isDialogShowed = false },
                offset = DpOffset(32.dp, 0.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Indonesia") },
                    onClick = {
                        isDialogShowed = false
                        SettingPreferences.currentLanguage = SettingPreferences.Language.ID
                        languageNameState = SettingPreferences.Language.ID
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "English") },
                    onClick = {
                        isDialogShowed = false
                        SettingPreferences.currentLanguage = SettingPreferences.Language.EN
                        languageNameState = SettingPreferences.Language.EN
                    }
                )
            }
        }
    }
}

//@Composable
//private fun OnBoardingContentTheme(
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = "Dark Mode",
//            textAlign = TextAlign.Center,
//            style = MaterialTheme.typography.headlineLarge,
//            fontWeight = FontWeight.Black
//        )
//        Spacer(modifier = Modifier.height(12.dp))
//        if (GlobalState.isDarkMode) {
//            Image(
//                painter = painterResource(id = R.drawable.dark_mode_active),
//                contentDescription = "Dark Mode"
//            )
//        }
//        if (!GlobalState.isDarkMode) {
//            Image(
//                painter = painterResource(id = R.drawable.light_mode_active),
//                contentDescription = "Dark Mode"
//            )
//        }
//        Spacer(modifier = Modifier.height(12.dp))
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = stringResource(R.string.txt_dark_mode_desc_onboarding),
//            style = MaterialTheme.typography.bodyLarge,
//            textAlign = TextAlign.Center
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Switch(
//                checked = GlobalState.isDarkMode,
//                onCheckedChange = {
//                    GlobalState.isDarkMode = it
//                    SettingsPreferences.isDarkMode = it
//                }
//            )
//        }
//    }
//}

@Composable
private fun OnBoardingContentComplete(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Finish",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier.size(256.dp),
            painter = painterResource(id = R.drawable.round_check_circle_outline_24),
            contentDescription = "Checkmark"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "You can use the app now!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        OnBoardingContentComplete()
    }
}