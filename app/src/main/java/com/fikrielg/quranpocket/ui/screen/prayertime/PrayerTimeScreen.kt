package com.fikrielg.quranpocket.ui.screen.prayertime

import android.Manifest
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fikrielg.quranpocket.ui.component.PrayerTimeCard
import com.fikrielg.quranpocket.ui.component.QuranPocketAppBar
import com.fikrielg.quranpocket.ui.theme.montserrat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.Locale.getDefault


@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun PrayerTimeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: PrayerTimeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (!locationPermissions.allPermissionsGranted) {
        LaunchedEffect(true) {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.getLocationUpdates()
        }
    }

    Scaffold(
        topBar = {
            QuranPocketAppBar(currentDestinationTitle = "Jadwal Sholat", navigateUp = { navigator.popBackStack() })
        }
    ) {
        Column(modifier = modifier
            .padding(it)
            .fillMaxSize()) {
            viewModel.currentLocation.collectAsState().let {
                @Suppress("DEPRECATION") val address = Geocoder(
                    context,
                    getDefault()
                ).getFromLocation(
                    it.value.latitude,
                    it.value.longitude,
                    1,
                )
                if (!address.isNullOrEmpty()) {
                    val locality = address.first().locality
                    val subLocality = address.first().subLocality
                    val subAdminArea = address.first().subAdminArea
                    val currentLocation = "$locality, $subLocality, $subAdminArea"
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentLocation,
                        textAlign = TextAlign.Center,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                viewModel.prayerTimeState.collectAsState(initial = PrayerTimeState.Idle)
                    .let { state ->
                        when (val event = state.value) {
                            is PrayerTimeState.Idle -> {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Column(
                                        modifier = Modifier.align(Alignment.Center),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Tolong aktifkan lokasi",
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center,
                                            fontFamily = montserrat
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            is PrayerTimeState.Error -> {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Column(
                                        modifier = Modifier.align(Alignment.Center),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = when (event.errorType) {
                                                ErrorType.NO_GPS -> "GPS Tidak Aktif"
                                                ErrorType.PERMISSION_ERROR -> "Tolong beri izin aplikasi ini untuk lokasi Anda"
                                                ErrorType.OTHERS -> "Error ketika mendapatkan lokasi"
                                            },
                                            textAlign = TextAlign.Center,
                                            fontFamily = montserrat
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Button(onClick = { viewModel.getLocationUpdates() }) {
                                            Text(text = "Retry?")
                                        }
                                    }
                                }
                            }

                            is PrayerTimeState.Success -> {
                                val sholatTime = event.data
                                PrayerTimeCard(timeEvent = "Shubuh", sholatTime = sholatTime.fajr)
                                Spacer(modifier = Modifier.height(16.dp))
                                PrayerTimeCard(timeEvent = "Dzuhur", sholatTime = sholatTime.dhuhr)
                                Spacer(modifier = Modifier.height(16.dp))
                                PrayerTimeCard(timeEvent = "Ashar", sholatTime = sholatTime.asr)
                                Spacer(modifier = Modifier.height(16.dp))
                                PrayerTimeCard(
                                    timeEvent = "Maghrib", sholatTime = sholatTime.maghrib
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                PrayerTimeCard(timeEvent = "Isya", sholatTime = sholatTime.isha)
                            }

                            else -> {}
                        }
                    }
            }
        }
    }
}