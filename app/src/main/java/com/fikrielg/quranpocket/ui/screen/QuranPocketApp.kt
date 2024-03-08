package com.fikrielg.quranpocket.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences
import com.fikrielg.quranpocket.ui.component.QuranPocketBottomNavigation
import com.fikrielg.quranpocket.ui.screen.destinations.HomeScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.OnBoardingScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.PrayerTimeScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.QiblaScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.ReadScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.SearchScreenDestination
import com.fikrielg.quranpocket.ui.screen.home.HomeScreen
import com.fikrielg.quranpocket.ui.screen.onboarding.OnBoardingScreen
import com.fikrielg.quranpocket.ui.screen.prayertime.PrayerTimeScreen
import com.fikrielg.quranpocket.ui.screen.qibla.QiblaScreen
import com.fikrielg.quranpocket.ui.screen.read.ReadScreen
import com.fikrielg.quranpocket.ui.screen.search.SearchScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import kotlinx.coroutines.CoroutineScope

@Composable
fun QuranPocketApp(
    navController: NavHostController = rememberNavController(),
) {
    val globalViewModel : GlobalViewModel = viewModel(LocalContext.current as ComponentActivity)
    Scaffold(
        bottomBar = {
            QuranPocketBottomNavigation(navController = navController)
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            modifier = Modifier.padding(it)
        ){
            composable(OnBoardingScreenDestination){
                OnBoardingScreen(navigator = destinationsNavigator)
            }
            composable(HomeScreenDestination){
                if(SettingPreferences.isOnBoarding){
                    destinationsNavigator.navigate(OnBoardingScreenDestination){
                        popUpTo(OnBoardingScreenDestination.route){
                            inclusive = true
                            saveState = true
                        }
                        restoreState = false
                        launchSingleTop = true
                    }
                }
                HomeScreen(navigator = destinationsNavigator, globalViewModel = globalViewModel)
            }
            composable(SearchScreenDestination){
                SearchScreen(navigator = destinationsNavigator)
            }
            composable(ReadScreenDestination){
                ReadScreen(navigator = destinationsNavigator, globalViewModel = globalViewModel)
            }
            composable(QiblaScreenDestination){
                QiblaScreen()
            }
            composable(PrayerTimeScreenDestination){
                PrayerTimeScreen(navigator = destinationsNavigator)
            }
            composable(SearchScreenDestination){
                SearchScreen(navigator = destinationsNavigator)
            }
        }
    }
}