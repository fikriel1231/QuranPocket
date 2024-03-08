package com.fikrielg.quranpocket.ui.screen.prayertime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.service.location.LocationClient
import com.fikrielg.quranpocket.service.location.LocationTrackerCondition

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.sql.Time
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val repository: QuranRepository,
    private val locationClient: LocationClient
) : ViewModel() {

    private val _prayerTimeState: MutableStateFlow<PrayerTimeState> =
        MutableStateFlow(PrayerTimeState.Idle)
    val prayerTimeState = _prayerTimeState.asStateFlow()

    private val _currentLocation: MutableStateFlow<CurrentLocation> =
        MutableStateFlow(CurrentLocation(0.0, 0.0))
    val currentLocation: StateFlow<CurrentLocation> = _currentLocation.asStateFlow()

    fun getLocationUpdates() {
        viewModelScope.launch {
            _prayerTimeState.emit(PrayerTimeState.Idle)
            locationClient.getLocationUpdates()
                .onEach { tracker ->
                    when (tracker) {
                        is LocationTrackerCondition.Error -> {
                            _prayerTimeState.emit(PrayerTimeState.Error(ErrorType.OTHERS))
                        }

                        is LocationTrackerCondition.MissingPermission -> {
                            _prayerTimeState.emit(PrayerTimeState.Error(ErrorType.PERMISSION_ERROR))
                        }

                        is LocationTrackerCondition.NoGps -> {
                            _prayerTimeState.emit(PrayerTimeState.Error(ErrorType.NO_GPS))
                        }

                        is LocationTrackerCondition.Success -> {
                            val latitude = tracker.location?.latitude
                            val longitude = tracker.location?.longitude
                            if (latitude == null || longitude == null) {
                                _prayerTimeState.emit(PrayerTimeState.Error(ErrorType.OTHERS))
                                return@onEach
                            }
                            _currentLocation.emit(CurrentLocation(longitude, latitude))
                            val responseResult = repository.getPrayerTime(
                                latitude.toString(),
                                longitude.toString()
                            )
                            _prayerTimeState.emit(PrayerTimeState.Success(responseResult.times[0]))
                            cancel()
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    data class CurrentLocation(
        val longitude: Double,
        val latitude: Double
    )
}

sealed class PrayerTimeState {
    data class Success(val data: com.fikrielg.quranpocket.data.source.remote.model.Time) : PrayerTimeState()
    data class Error(val errorType: ErrorType) : PrayerTimeState()
    object Idle: PrayerTimeState()
}

enum class ErrorType {
    NO_GPS,
    PERMISSION_ERROR,
    OTHERS
}