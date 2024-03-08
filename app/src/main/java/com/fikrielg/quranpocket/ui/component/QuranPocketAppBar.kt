package com.fikrielg.quranpocket.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.ui.theme.montserrat


data class TopBarActionItem(
    val icon: Painter,
    val text: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranPocketAppBar(
    currentDestinationTitle: String,
    navigateUp: () -> Unit,
    isHomeScreen: Boolean = false,
    actions:  @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = currentDestinationTitle,
                fontFamily = montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )
        },
        navigationIcon = {
            if (!isHomeScreen)
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
                        contentDescription = "Back",
                    )
                }
        },
        actions = { actions() },
    )
}