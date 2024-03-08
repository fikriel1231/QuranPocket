package com.fikrielg.quranpocket.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import com.fikrielg.quranpocket.data.source.local.entities.Juz
import com.fikrielg.quranpocket.data.source.local.entities.Page
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import com.fikrielg.quranpocket.data.source.remote.model.PrayerTimeResponse
import kotlinx.coroutines.flow.Flow

interface QuranRepository {

    fun showQuranBySurah(): Flow<List<Surah>>

    fun showQuranByJuz(): Flow<List<Juz>>

    fun showQuranByPage(): Flow<List<Page>>

    fun readAyahBySurah(surahNumber: Int): Flow<List<Quran>>

    fun readAyahByJuz(juzNumber: Int): Flow<List<Quran>>

    fun readAyahByPage(pageNumber: Int): Flow<List<Quran>>

    fun getBookmarkList(): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)

    suspend fun deleteAllBookmark()

    fun searchSurah(search:String): Flow<List<Surah>>

    fun searchEntireSurah(search:String): Flow<List<Quran>>
    suspend fun getPrayerTime(latitude: String, longitude: String): PrayerTimeResponse
}