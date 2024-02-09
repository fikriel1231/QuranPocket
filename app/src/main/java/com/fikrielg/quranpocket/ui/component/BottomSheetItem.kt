package com.fikrielg.quranpocket.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrielg.quranpocket.ui.theme.montserrat

@Composable
fun BottomSheetItem(title: String, @DrawableRes resourceId: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = resourceId), contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontFamily = montserrat,
            fontSize = 20.sp,
        )
    }
}