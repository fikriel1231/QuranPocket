package com.fikrielg.quranpocket.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fikrielg.quranpocket.ui.theme.montserrat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    title: String?,
    message: String?,
    dismissText: String?,
    exitText: String?
) {
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .height(IntrinsicSize.Min),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = title ?: "",
                    modifier = Modifier
                        .padding(8.dp, 16.dp, 8.dp, 2.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(), fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontFamily = montserrat
                )
                Text(
                    text = message ?: "",
                    modifier = Modifier
                        .padding(8.dp, 2.dp, 8.dp, 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = montserrat
                )
                Divider(color = Color.Gray, modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp))
                Row(Modifier.padding(top = 0.dp)) {
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(
                            onClick = { onDismiss() },
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .weight(1F)
                                .border(0.dp, Color.Transparent)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(0.dp, 0.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = exitText ?: "Cancel",
                                color = Color.Blue,
                                fontFamily = montserrat
                            )
                        }
                    }
                    Divider(
                        color = Color.Gray, modifier =
                        Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(
                            onClick = {
                                onExit.invoke()
                            },
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .weight(1F)
                                .border(0.dp, color = Color.Transparent)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(0.dp, 0.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues()
                        ) {
                            Text(
                                text = dismissText ?: "",
                                color = Color.Red,
                                fontFamily = montserrat
                            )
                        }
                    }
                }
            }
        }
    }
}