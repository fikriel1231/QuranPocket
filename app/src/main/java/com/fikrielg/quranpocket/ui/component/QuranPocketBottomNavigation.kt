package com.fikrielg.quranpocket.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.ui.screen.NavGraphs
import com.fikrielg.quranpocket.ui.screen.appCurrentDestinationAsState
import com.fikrielg.quranpocket.ui.screen.destinations.BookmarkScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.Destination
import com.fikrielg.quranpocket.ui.screen.destinations.HomeScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.QiblaScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.SearchScreenDestination
import com.fikrielg.quranpocket.ui.screen.destinations.SettingScreenDestination
import com.fikrielg.quranpocket.ui.screen.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import io.github.alexzhirkevich.cupertino.CupertinoIconDefaults
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.filled.Bookmark
import io.github.alexzhirkevich.cupertino.icons.filled.Homepod
import io.github.alexzhirkevich.cupertino.icons.filled.House



enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    val label: String
) {
    Home(HomeScreenDestination, R.drawable.home, "Home"),
    Search(SearchScreenDestination, R.drawable.search, "Search"),
    Bookmark(SettingScreenDestination, R.drawable.cog, "Setting"),
}



@Composable
fun QuranPocketBottomNavigation(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painterResource(id = destination.icon), contentDescription = destination.label)},
                label = { Text(destination.label) },
            )
        }
    }
}