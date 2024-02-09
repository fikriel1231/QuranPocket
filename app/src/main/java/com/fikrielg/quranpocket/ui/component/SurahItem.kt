package com.fikrielg.quranpocket.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.ui.theme.uthmanic

@Composable
fun SurahItem(
    surah:Surah,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(surah.surahNumber ?: 0) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    Modifier.size(40.dp)
                )
                Text(
                    text = surah.surahNumber.toString(),
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = surah.surahNameEn ?: "", fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "${surah.surahNameId ?: ""} · ${surah.ayahTotal} Ayat",
                    fontFamily = montserrat,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = surah.surahNameAr ?: "", fontFamily = uthmanic, fontSize = 22.sp)
    }
}