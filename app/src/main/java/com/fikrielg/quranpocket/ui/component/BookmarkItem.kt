package com.fikrielg.quranpocket.ui.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.ui.theme.uthmanic
import com.fikrielg.quranpocket.utils.Converters

@Composable
fun BookmarkItem(
    bookmark: Bookmark,
    onClickDelete: () -> Unit,
    navigateToRead: (
        surahNumber: Int?,
        scrollPosition: Int?
    ) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                navigateToRead(
                    bookmark.surahNumber,
                    bookmark.position
                )
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Date Added: ${Converters.convertMillisToActualDate(bookmark.dateAdded ?: 0L)}",
                fontFamily = montserrat,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "${bookmark.surahName} : ${bookmark.ayahNumber}",
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(
                    onClick = { onClickDelete() },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete Bookmark",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column() {
                Text(
                    text = bookmark.ayahText ?: "",
                    fontFamily = uthmanic,
                    fontSize = 30.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = bookmark.translationId ?: "",

                    fontFamily = montserrat,
                    color = Color.Gray,
                )


            }

        }
    }
}