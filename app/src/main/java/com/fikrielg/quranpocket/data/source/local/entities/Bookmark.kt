package com.fikrielg.quranpocket.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fikrielg.quranpocket.ui.screen.home.ORDER_BY_SURAH

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val surahName: String? = "",
    val surahNumber: Int? = 0,
    val ayahNumber: Int? = 0,
    val ayahText: String? = "",
    val translationId: String? = "",
    val position: Int? = 0,
    val dateAdded: Long? = System.currentTimeMillis(),
)