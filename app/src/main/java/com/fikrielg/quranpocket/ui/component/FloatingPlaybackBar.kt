package com.fikrielg.quranpocket.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarButtonIconSize
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarButtonIconSizeSmall
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarButtonSize
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarButtonSizeSmall
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarCoverSize
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarHeight
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarPrimaryTextStyle
import com.fikrielg.quranpocket.ui.constants.FloatingPlaybackBarSecondaryTextStyle
import com.fikrielg.quranpocket.ui.constants.LargeDp
import com.fikrielg.quranpocket.ui.constants.MediumDp
import com.fikrielg.quranpocket.ui.constants.PlaybackBarColor
import com.fikrielg.quranpocket.ui.constants.PrimaryWhite
import com.fikrielg.quranpocket.ui.constants.SmallDp
import com.fikrielg.quranpocket.ui.screen.read.PlayStatus


@Composable
fun FloatingPlaybackBar(
    surahName: String?,
    ayahNumber: String?,
    qoriName: String?,
    qoriImage: String?,
    onPreviousClicked: () -> Unit = {},
    onPlayPauseClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {},
    onStopClicked:() -> Unit = {},
    isPaused: PlayStatus = PlayStatus.NOT_PLAYING
) {

    Card(
        modifier = Modifier
            .padding(MediumDp)
            .height(FloatingPlaybackBarHeight)
            .background(Color.Transparent)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = LargeDp),
        shape = RoundedCornerShape(LargeDp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(PlaybackBarColor)
                .padding(MediumDp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(FloatingPlaybackBarCoverSize)
                    .clip(RoundedCornerShape(MediumDp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = qoriImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = LargeDp)
                    .weight(1f)
            ) {
                Text(
                    style = FloatingPlaybackBarPrimaryTextStyle,
                    text = "QS ${surahName}: ${ayahNumber}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    style = FloatingPlaybackBarSecondaryTextStyle,
                    text = qoriName ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // 6. Wrapping Icon inside IconButton
            IconButton(
                onClick = { onPreviousClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSize)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_skip_previous_24),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSize)
                )
            }
            // 7. Responsive Control Icons
            val iconId =
                if (isPaused == PlayStatus.IS_PLAYING) {
                    R.drawable.round_pause_24
                } else {
                    R.drawable.round_play_arrow_24
                }
            IconButton(
                onClick = { onPlayPauseClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSize)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSize)
                )
            }
            IconButton(
                onClick = { onNextClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSize)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_skip_next_24),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSize)
                )
            }
            IconButton(
                onClick = { onStopClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSizeSmall)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_clear_24),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSizeSmall)
                )
            }
        }
    }
}



