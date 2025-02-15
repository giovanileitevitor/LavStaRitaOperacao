package br.lavstaritaoperacao.aux

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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

fun formatCurrency(editText: EditText) {
    editText.addTextChangedListener(object : TextWatcher {
        private var current = ""

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (s.toString() != current) {
                val formatted = formatCurrencyValue(s.toString())
                current = formatted
                editText.setText(formatted)
                editText.setSelection(formatted.length)
            }
        }
    })
}

private fun formatCurrencyValue(value: String): String {
    val cleanString = value.replace(Regex("[^\\d]"), "")
    val parsed = if (cleanString.isEmpty()) 0.0 else cleanString.toDouble() / 100
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatter.format(parsed)
}