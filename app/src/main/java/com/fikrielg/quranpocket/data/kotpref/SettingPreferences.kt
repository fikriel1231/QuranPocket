package com.fikrielg.quranpocket.data.kotpref

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumOrdinalPref

object SettingPreferences : KotprefModel() {

    private const val SLIDER_DEF_VALUE = 32F

    var currentQori by enumOrdinalPref(Qori.ABDUL_BASIT)
    var currentLanguage by enumOrdinalPref(Language.ID)
    var isFocusReadActive by booleanPref(false)
    var ayahTextSize by floatPref(SLIDER_DEF_VALUE)
    var isOnBoarding by booleanPref(true)

    override fun clear() {
        super.clear()
        currentLanguage = Language.ID
        ayahTextSize = SLIDER_DEF_VALUE
        currentQori = Qori.ABDUL_BASIT
        isFocusReadActive = false
    }

    enum class Language(
        val tag: String,
        val language: String
    ){
        ID(
            "id",
            "Indonesia"
        ),
        EN(
            "en-US",
            "English"
        )
    }

    enum class Qori(
        val qoriName: String,
        val qoriId: String,
        val qoriImage: String
    ){
        ABDUL_BASIT(
            qoriName = "Abdul Basit Abdul Samad",
            qoriId = "AbdulSamad_64kbps_QuranExplorer.Com",
            qoriImage = "https://4.bp.blogspot.com/-4lIC_gwG9hc/Wm1p5GFQifI/AAAAAAAABSc/VyQ4leiOxOouuyjmsXo8vHCbe3kgZs5sACK4BGAYYCw/s1600/syaikh%2BAbdul%2BBasit%2BAbdus%2BSamad%2BMujawaad%2Bfree%2Bdownload.jpg"
        ),
        ABDULLAH_BASFAT(
            qoriName = "Abdullah Basfar",
            qoriId = "Abdullah_Basfar_64kbps",
            qoriImage = ""
        ),
        ABDURRAHMAHAN_SUDAIS(
            qoriName = "Abdurrahmaan As Sudais",
            qoriId = "Abdurrahmaan_As-Sudais_64kbps",
            qoriImage = ""
        ),
        ABU_BAKR(
            qoriName = "Abu Bakr Ash Shaatree",
            qoriId = "Abu_Bakr_Ash-Shaatree_64kbps",
            qoriImage = ""
        ),
        ALI_JABER(
            qoriName = "Ali Jaber",
            qoriId = "Ali_Jaber_64kbps",
            qoriImage = ""
        )
    }



}

