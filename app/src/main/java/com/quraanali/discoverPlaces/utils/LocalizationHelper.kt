package com.quraanali.discoverPlaces.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import java.util.*


/**
 * Created by Ali Alqur'an
 *
 * quraanali@gmail.com
 */

object LocalizationHelper {
    const val LANG_ARABIC = "ar"
    private const val LANG_ENGLISH = "en"

    private const val PREF_LOCALE = "PREF_LOCALE"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    private fun setLocale(c: Context, langCode: String): Context {
        var context = c
        val configuration = Configuration()

        val locale = Locale(langCode)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
        }
        context = context.createConfigurationContext(configuration)

        return context
    }

    fun onAttach(context: Context): Context {
        val lang = getCurrentLocale(context)
        return setLocale(context, lang)
    }

    fun switchLocale(context: Context, langCode: String) {
        val sp = getSharedPreferences(context)
        sp.edit().putString(PREF_LOCALE, langCode).apply()
    }

    fun getCurrentLocale(context: Context): String {
        val sp = getSharedPreferences(context)
        return sp.getString(PREF_LOCALE, /*Default Localization*/ getCurrentLanguage())
            ?: getCurrentLanguage()
    }

    @SuppressLint("ApplySharedPref")
    fun switchLocale(context: Context) {   // U can use this method if u have just two Languages
        val langCode = if (getCurrentLocale(context) == LANG_ARABIC) LANG_ENGLISH else LANG_ARABIC
        val sp = getSharedPreferences(context)
        sp.edit().putString(PREF_LOCALE, langCode).commit()
    }

    private fun getCurrentLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault()[0].language
        } else {
            Locale.getDefault().language
        }
    }
}