package br.lavstaritaoperacao.aux

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

inline fun View.onDebouncedListener(
    delayInClick: Long = 500L,
    crossinline listener: (View) -> Unit){

    val enableAgain = Runnable { isEnabled = true }

    setOnClickListener{
        if(isEnabled){
            isEnabled = false
            postDelayed(enableAgain, delayInClick)
            listener(it)
        }
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun getDateAndTime(): String {
    val dataHoraAtual = LocalDateTime.now()
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return dataHoraAtual.format(formato)
}