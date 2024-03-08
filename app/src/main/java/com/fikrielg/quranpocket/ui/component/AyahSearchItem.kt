package com.fikrielg.quranpocket.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.ui.theme.uthmanic

@Composable
fun AyahSearchItem(
    quran: Quran,
    onClick: () -> Unit,
    searchText: String
) {

    val translationText = when{
        SettingPreferences.currentLanguage == SettingPreferences.Language.EN -> quran.translation_en ?: ""
        else -> quran.translation_id ?: ""
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {

            Text(
                text = "QS ${quran.surahNameEn} : ${quran.ayahNumber}",
                fontFamily = montserrat,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "\u202E${quran.ayahText ?: ""}\u202C",
                fontFamily = uthmanic,
                textAlign = TextAlign.Right,
                fontSize = 30.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = AnnotatedString.Builder().run {
                    pushStyle(SpanStyle(fontSize = 18.sp))

                    val words = translationText.split(" ")
                    for (word in words) {
                        val trimmedWord = word.trim{ it <= ' '}
                        val highlighted = trimmedWord.contains(searchText, ignoreCase = true)

                        if (highlighted) {
                            pushStyle(SpanStyle(background = Color.Yellow))
                        }

                        append("$word ")

                        if (highlighted) {
                            pop()
                        }
                    }

                    toAnnotatedString()
                },
                fontFamily = montserrat
            )


        }
    }

}
