package com.fikrielg.quranpocket.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.fikrielg.quranpocket.data.source.local.entities.Juz
import com.fikrielg.quranpocket.data.source.local.entities.Page
import com.fikrielg.quranpocket.data.source.local.entities.Quran
import com.fikrielg.quranpocket.data.source.local.entities.Surah
import kotlinx.coroutines.flow.Flow


@Dao
interface QuranDao {

    @Query("SELECT * FROM Surah")
    fun showQuranBySurah() : Flow<List<Surah>>

    @Query("SELECT * FROM Juz")
    fun showQuranByJuz() : Flow<List<Juz>>

    @Query("SELECT * FROM Page")
    fun showQuranByPage() : Flow<List<Page>>

    @Query("SELECT id, sora, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, translation_en, footnotes_en, footnotes_id, sora_descend_place, sora_name_en, sora_name_id FROM quran WHERE sora = :surahNumber")
    fun readAyahBySurah(surahNumber:Int): Flow<List<Quran>>

    @Query("SELECT id, sora, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, translation_en, footnotes_en, footnotes_id, sora_descend_place sora_name_en, sora_name_id FROM quran WHERE jozz = :juzNumber")
    fun readAyahByJuz(juzNumber:Int): Flow<List<Quran>>

    @Query("SELECT id, sora, page, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, translation_en, footnotes_en, footnotes_id, sora_descend_place sora_name_en, sora_name_id FROM quran WHERE page = :pageNumber")
    fun readAyahByPage(pageNumber:Int): Flow<List<Quran>>
}