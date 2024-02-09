package com.fikrielg.quranpocket.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView(
    "SELECT id, sora, sora_name_ar, sora_name_en, sora_name_id, COUNT(id) as ayah_total, sora_descend_place FROM quran GROUP by sora"
)

data class Surah(
    @PrimaryKey val id: Int? = 0,
    @ColumnInfo("sora") val surahNumber: Int? = 0,
    @ColumnInfo("ayah_total") val ayahTotal: Int? = 0,
    @ColumnInfo("sora_name_ar") val surahNameAr: String? = "",
    @ColumnInfo("sora_name_en") val surahNameEn: String? = "",
    @ColumnInfo("sora_name_id") val surahNameId: String? = "",
    @ColumnInfo("sora_descend_place") val surahDescendPlace: String? = "",

    )