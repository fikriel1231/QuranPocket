package com.fikrielg.quranpocket.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fikrielg.quranpocket.data.source.local.entities.Juz
import com.fikrielg.quranpocket.data.source.local.entities.Page
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah


@Database(
    entities = [Quran::class],
    version = 1,
    views = [Surah::class, Juz::class, Page::class]
)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
}