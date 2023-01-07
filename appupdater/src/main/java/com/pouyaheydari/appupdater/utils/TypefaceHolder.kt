package com.pouyaheydari.appupdater.utils

import android.graphics.Typeface

object TypefaceHolder {

    private var typeface: Typeface? = null

    fun getTypeface() = typeface

    fun setTypeface(typeface: Typeface?) {
        TypefaceHolder.typeface = typeface
    }

    fun clear() {
        typeface = null
    }
}
