package com.fikrielg.quranpocket.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey


@DatabaseView("SELECT MIN(id) as idMin, jozz, aya_no, sora, aya_text, sora_name_ar, sora_name_en, aya_no FROM quran GROUP BY jozz ORDER BY idMin ASC")
data class Juz(
    @PrimaryKey val id: Int? = 0,
    @ColumnInfo("jozz") val juzNumber: Int? = 0,
    @ColumnInfo("sora") val surahNumber: Int? = 0,
    @ColumnInfo("aya_text") val ayahText: String? = "",
    @ColumnInfo("sora_name_en") val surahNameEn: String? = "",
    @ColumnInfo("sora_name_ar") val surahNameAr: String? = "",
    @ColumnInfo("aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo("aya_total") val ayahTotal: Int? = 0,
)