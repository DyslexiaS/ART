package com.example.dyslexia.art

import android.view.View
import android.app.Activity
import android.support.annotation.IdRes

fun <T : View> Activity.bind(@IdRes res : Int) : Lazy<T> {
    return lazy { findViewById<T>(res) }
}
fun <T : View> View.bind(@IdRes res : Int) : Lazy<T> {
    return lazy { findViewById<T>(res) }
}