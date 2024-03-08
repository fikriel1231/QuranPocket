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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.kotpref.SettingPreferences
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.ui.theme.uthmanic

@Composable
fun AyahItem(
    quran: Quran,
    totalAyah: Int,
    onClick: () -> Unit,
    onPlayAll: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (quran.ayahNumber == 1) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()

            ) {
                Image(
                    painter = painterResource(id = if (quran.surahDescendPlace == "Meccan") R.drawable.mecca_bg else R.drawable.madina_bg),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = quran.surahNameEn ?: "",
                        fontFamily = montserrat,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp
                    )
                    Text(
                        text = quran.surahNameId ?: "",
                        fontFamily = montserrat,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "$totalAyah Ayat",
                        fontFamily = montserrat,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
                IconButton(
                    onClick = { onPlayAll() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .background(color = Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play All",
                        tint = Color(0xFF161670)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.bismillah_cali),
                modifier = Modifier.width(220.dp),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "\u202E${quran.ayahText ?: ""}\u202C",
                fontFamily = uthmanic,
                textAlign = TextAlign.Right,
                fontSize = 30.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (SettingPreferences.currentLanguage == SettingPreferences.Language.ID) quran.translation_id
                    ?: "" else quran.translation_en ?: "",
                fontFamily = montserrat
            )
        }
    }

}
