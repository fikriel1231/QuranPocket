package com.fikrielg.quranpocket.utils

import com.fikrielg.quranpocket.data.source.local.entities.Juz
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Converters {
    fun convertMillisToActualDate(
        dateAdded: Long
    ): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateAdded))
    }

    fun convertNumberToThreeDigits(
        number: Int
    ): String {
        return String.format("%03d", number)
    }

    fun List<Juz>.mapToJuzIndexing(): List<JuzWithSurahIndex> {
        val groupedList = this.groupBy { it.juzNumber }
        return groupedList.map { (juzNumber, juzList) ->
            JuzWithSurahIndex(
                juzNumber,
                juzList.map { "${it.surahNameEn}: ${it.surahNumber}" },
                juzList.map { it.surahNumber }
            )
        }
    }

    data class JuzWithSurahIndex(
        val juzNumber: Int?,
        val surahList: List<String?>,
        val surahNumberList: List<Int?>
    )
}