package com.fikrielg.quranpocket.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey


@DatabaseView("SELECT MIN(id) as id, page, sora, aya_no, sora_name_en, sora_name_ar FROM quran GROUP by page ORDER BY id")
class Page(
    @PrimaryKey val id: Int? = 0,
    @ColumnInfo("page") val pageNumber: Int? = 0,
    @ColumnInfo("sora") val surahNumber: Int? = 0,
    @ColumnInfo("aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo("sora_name_en") val surahNameEn: String? = "",
    @ColumnInfo("sora_name_ar") val surahNameAr: String? = "",
)