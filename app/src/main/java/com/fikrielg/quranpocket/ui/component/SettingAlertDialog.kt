package com.fikrielg.quranpocket.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingAlertDialog(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    currentSelected: String,
    content: @Composable () -> Unit,
    confirmButtonText: String = "",
    dismissButtonText: String = "",
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        icon = {
            Icon(painter = painterResource(id = icon), contentDescription = "")
        },
        title = {
            Text(text = title)
        },
        onDismissRequest = onDismissClick,
        confirmButton = {
            if (confirmButtonText.isNotEmpty())
                TextButton(onClick = onConfirmClick) {
                    Text(text = confirmButtonText)
                }
        },
        dismissButton = {
            if (dismissButtonText.isNotEmpty())
                TextButton(onClick = onDismissClick) {
                    Text(text = dismissButtonText)
                }
        },
        text = content
    )
}

@Composable
fun ActionItem(
    text: String,
    onClick: () -> Unit,
    buttonColors: Color = MaterialTheme.colorScheme.surface,
) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

data class Action(
    val text: String,
    val onClick: () -> Unit,
)