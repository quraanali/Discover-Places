package com.quraanali.discoverPlaces.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

fun Activity.isArabic(): Boolean {
    return LocalizationHelper.getCurrentLocale(this) == LocalizationHelper.LANG_ARABIC
}

fun Fragment.isArabic(): Boolean {
    return LocalizationHelper.getCurrentLocale(requireContext()) == LocalizationHelper.LANG_ARABIC
}

fun Double.formatCurrency(decimal: Int): String {
    return try {
        String.format(Locale.ENGLISH, "%.${decimal}f", round(decimal))
    } catch (e: Exception) {
        ""
    }
}

fun Double.round(decimal: Int): Double {
    var multiplier = 1.0
    repeat(decimal) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun EditText.textChanges(): MutableLiveData<String> {
    val liveData = MutableLiveData<String>()

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            liveData.value = text.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //Do Nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //Do Nothing
        }

    })
    return liveData
}

fun <T> LiveData<T>.debounce(duration: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}

fun String.formatDateToLocal(dateFormat: String): String {
    val df = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    df.timeZone = TimeZone.getTimeZone("UTC")
    val date = df.parse(this)
    df.timeZone = TimeZone.getDefault()
    return date?.let { df.format(it) } ?: ""
}


fun Fragment.vibrateDevice() {
    val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            activity?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vib.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        //deprecated in API 26
        val pattern = longArrayOf(0, 600, 10, 600)
        vib.vibrate(pattern, -1)
    }
}

fun View.setIsEnabled(isEnabled: Boolean) {
    this.isClickable = isEnabled
    this.alpha = if (isEnabled) 1f else 0.6f
}

fun View.showKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}

fun Context.getLocaleStringResource(
    requestedLocale: Locale?,
    resourceId: Int,
): String {
    val result: String
    val config =
        Configuration(resources.configuration)
    config.setLocale(requestedLocale)
    result = createConfigurationContext(config).getText(resourceId).toString()

    return result
}