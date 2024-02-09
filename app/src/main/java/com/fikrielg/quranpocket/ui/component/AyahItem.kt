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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.fikrielg.quranpocket.ui.theme.uthmanic

@Composable
fun AyahItem(
    ayahNumber: Int?,
    ayahTextAr: String?,
    ayahTextId: String?,
    surahName: String?,
    surahNameId: String?,
    totalAyah: Int,
    onClick: () -> Unit,
) {
    Column() {
        if (ayahNumber == 1)
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = Color(0xff5F94C6))
                    .fillMaxWidth()

            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = surahName ?: "",
                            fontFamily = montserrat,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 23.sp
                        )
                        Text(
                            text = "($surahNameId)",
                            fontFamily = montserrat,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$totalAyah Ayat",
                        fontFamily = montserrat,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Divider(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيْمِ",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = uthmanic,
                        fontSize = 30.sp,
                        color = Color.White
                    )

                }
            }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth()
                .clickable { onClick() },
            horizontalAlignment = Alignment.End
        ) {

//            Row(
//                horizontalArrangement = Arrangement.End,
//                modifier = Modifier.fillMaxWidth()
//            ) {

//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.End
//                ) {
//                    Row {
//                        IconButton(onClick = { onPlay() }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_play_circle_outline_24),
//                                contentDescription = "Play Ayat"
//                            )
//                        }
//                        IconButton(onClick = { onBookmark() }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.round_bookmark_border_24),
//                                contentDescription = "Bookmark Ayat"
//                            )
//                        }
//                        IconButton(onClick = { onShare() }) {
//                            Icon(
//                                imageVector = Icons.Default.Share,
//                                contentDescription = "Share Ayat"
//                            )
//                        }
//                        IconButton(onClick = { onCopy() }) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.round_content_copy_24),
//                                contentDescription = "Copy Ayat"
//                            )
//                        }
//                    }
//
//                }
//            }
                Text(
                    text = ayahTextAr ?: "",
                    fontFamily = uthmanic,
                    textAlign = TextAlign.Right,
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = ayahTextId ?: "",
                    fontFamily = montserrat
                )
            }
        }

    }
