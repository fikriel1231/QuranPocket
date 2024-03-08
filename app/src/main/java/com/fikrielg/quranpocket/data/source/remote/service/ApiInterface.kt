package com.fikrielg.quranpocket.data.source.remote.service

import com.fikrielg.quranpocket.data.source.remote.model.PrayerTimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("day")
    suspend fun getAdzanSchedule(
        @Query("latitude") latitude:String,
        @Query("longitude") longitude:String
    ): PrayerTimeResponse
}