package com.brunoaybar.notekeeper.ui.extensions

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager



inline fun <reified T: Activity> Activity.start(bundle: Bundle? = null){
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(it) }
    startActivity(intent)
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.getSafeColor(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun View.listenKeyboardChanges(keyboardOpened: (()->Unit)? = null,
                               keyboardClosed: (()->Unit)? = null){
    viewTreeObserver.addOnGlobalLayoutListener({
        val r = Rect()
        getWindowVisibleDisplayFrame(r)
        val screenHeight = getRootView().getHeight()

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - r.bottom

        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
            keyboardOpened?.invoke()
        } else {
            keyboardClosed?.invoke()
        }
    })
}