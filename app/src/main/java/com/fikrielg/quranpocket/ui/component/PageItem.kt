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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.source.local.entities.Page
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.ui.theme.uthmanic

@Composable
fun PageItem(
    page: Page,
    nextPage: Page?,  // Informasi halaman selanjutnya
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
            .clickable { onClick(page.pageNumber ?: 0) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            val surahNameEnd = nextPage?.surahNameEn ?: ""
            Text(
                text = "Halaman ${page.pageNumber}",
                fontFamily = montserrat,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${page.surahNameEn} ${page.ayahNumber}${
                    if (nextPage?.ayahNumber?.minus(
                            1
                        ) == 0
                    ) "" else "  - ${surahNameEnd} ${nextPage?.ayahNumber?.minus(1)}"
                }",
                fontFamily = montserrat,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}

