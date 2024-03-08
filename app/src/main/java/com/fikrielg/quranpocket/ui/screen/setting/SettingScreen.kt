package com.fikrielg.quranpocket.ui.screen.setting

import androidx.compose.runtime.Composable

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences.currentLanguage
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences.currentQori
import com.fikrielg.quranpocket.ui.component.ActionItem
import com.fikrielg.quranpocket.ui.component.ClickableCardSettings
import com.fikrielg.quranpocket.ui.component.QuranPocketAppBar
import com.fikrielg.quranpocket.ui.component.SettingAlertDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingScreen(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current
    var isLanguageDialogShow by remember {
        mutableStateOf(false)
    }

    var isQariDialogShow by remember {
        mutableStateOf(false)
    }

    var selectedLanguage by remember {
        mutableStateOf(currentLanguage)
    }

    var selectedQari by remember {
        mutableStateOf(currentQori)
    }

    val scrollState = rememberScrollState()

    Scaffold(topBar = {
      QuranPocketAppBar(currentDestinationTitle = "Settings", navigateUp = { /*TODO*/ }, isHomeScreen = true)
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {



            ClickableCardSettings(label = "Language",
                description = "Change Language",
                icon = R.drawable.round_language_24,
                currentValue = selectedLanguage.language,
                onClick = { isLanguageDialogShow = true })

            ClickableCardSettings(label = "Qori",
                description = "Change Qori",
                icon = R.drawable.book_open,
                currentValue = selectedQari.qoriName,
                onClick = { isQariDialogShow = true })

            if (isLanguageDialogShow) {
                val options = listOf(
                    OptionLanguage(
                        text = SettingPreferences.Language.ID.language, language = SettingPreferences.Language.ID
                    ),
                    OptionLanguage(
                        text = SettingPreferences.Language.EN.language, language = SettingPreferences.Language.EN
                    ),
                )

                SettingAlertDialog(
                    icon = R.drawable.round_language_24,
                    title = "Language",
                    currentSelected = currentLanguage.language,
                    content = {
                        LazyColumn(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(), content = {
                            items(options) { option ->
                                ActionItem(
                                    text = option.text,
                                    onClick = {
                                        isLanguageDialogShow = false
                                        currentLanguage = option.language
                                        selectedLanguage = option.language
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            context.getSystemService(LocaleManager::class.java).applicationLocales =
                                                LocaleList.forLanguageTags(option.language.tag)

                                        } else {
                                            AppCompatDelegate.setApplicationLocales(
                                                LocaleListCompat.forLanguageTags(option.language.tag)
                                            )
                                        }
                                    },
                                    buttonColors = if (currentLanguage.language == option.text) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                )
                            }
                        })
                    },
                    onDismissClick = { isLanguageDialogShow = false },
                    onConfirmClick = { isLanguageDialogShow = false },
                    dismissButtonText = "Cancel"
                )
            }

            if (isQariDialogShow) {
                val options = listOf(
                    OptionQori(
                        text = SettingPreferences.Qori.ABDUL_BASIT.qoriName, qori = SettingPreferences.Qori.ABDUL_BASIT
                    ),
                    OptionQori(
                        text = SettingPreferences.Qori.ABDULLAH_BASFAT.qoriName, qori = SettingPreferences.Qori.ABDULLAH_BASFAT
                    ),
                    OptionQori(
                        text = SettingPreferences.Qori.ABU_BAKR.qoriName, qori = SettingPreferences.Qori.ABU_BAKR
                    ),
                    OptionQori(
                        text = SettingPreferences.Qori.ALI_JABER.qoriName, qori = SettingPreferences.Qori.ALI_JABER
                    ),
                    OptionQori(
                        text = SettingPreferences.Qori.ABDURRAHMAHAN_SUDAIS.qoriName, qori = SettingPreferences.Qori.ABDURRAHMAHAN_SUDAIS
                    ),
                )

                SettingAlertDialog(
                    icon = R.drawable.book_open,
                    title = "Qori",
                    currentSelected = selectedQari.qoriName,
                    content = {
                        LazyColumn(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(), content = {
                            items(options) { option ->
                                ActionItem(
                                    text = option.text,
                                    onClick = {
                                        isQariDialogShow = false
                                        currentQori = option.qori
                                        selectedQari = option.qori
                                    },
                                    buttonColors = if (currentLanguage.language == option.text) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                )
                            }
                        })
                    },
                    onDismissClick = { isQariDialogShow = false },
                    onConfirmClick = { isQariDialogShow = false },
                    dismissButtonText = "Cancel"
                )
            }
        }
    }
}

data class OptionLanguage(
    val text: String,
    val language: SettingPreferences.Language,
)

data class OptionQori(
    val text: String,
    val qori: SettingPreferences.Qori,
)