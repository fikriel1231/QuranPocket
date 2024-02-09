package com.fikrielg.quranpocket.data.repository

import com.fikrielg.quranpocket.data.source.local.BookmarkDatabase
import com.fikrielg.quranpocket.data.source.local.QuranDatabase
import com.fikrielg.quranpocket.data.source.local.entities.Bookmark
import com.fikrielg.quranpocket.data.source.local.entities.Juz
import com.fikrielg.quranpocket.data.source.local.entities.Page
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val quranDatabase: QuranDatabase,
    private val bookmarkDatabase: BookmarkDatabase
) : QuranRepository {
    override fun showQuranBySurah(): Flow<List<Surah>> {
        return quranDatabase.quranDao().showQuranBySurah()
    }

    override fun showQuranByJuz(): Flow<List<Juz>> {
        return quranDatabase.quranDao().showQuranByJuz()
    }

    override fun showQuranByPage(): Flow<List<Page>> {
        return quranDatabase.quranDao().showQuranByPage()
    }

    override fun readAyahBySurah(surahNumber: Int): Flow<List<Quran>> {
        return quranDatabase.quranDao().readAyahBySurah(surahNumber)
    }

    override fun readAyahByJuz(juzNumber: Int): Flow<List<Quran>> {
        return quranDatabase.quranDao().readAyahByJuz(juzNumber)
    }

    override fun readAyahByPage(pageNumber: Int): Flow<List<Quran>> {
        return quranDatabase.quranDao().readAyahByPage(pageNumber)
    }

    override fun getBookmarkList(): Flow<List<Bookmark>> {
        return bookmarkDatabase.bookmarkDao().getBookmarkList()
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return bookmarkDatabase.bookmarkDao().insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return bookmarkDatabase.bookmarkDao().deleteBookmark(bookmark)
    }

    override suspend fun deleteAllBookmark() {
        return bookmarkDatabase.bookmarkDao().deleteAllBookmark()
    }

}