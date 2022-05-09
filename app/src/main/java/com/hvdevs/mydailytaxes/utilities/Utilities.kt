package com.hvdevs.mydailytaxes.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.hvdevs.mydailytaxes.R

object Utilities {

    @SuppressLint("ClickableViewAccessibility")
    @JvmStatic
    fun setupUI(view: View, activity: FragmentActivity?) {
        // si estamos no estamos en una instancia de EditText y tocamos la pantalla, el teclado se escondera.
        if (view !is TextInputEditText) {
            // colocamos una escucha para detectar los toques en la pantalla
            view.setOnTouchListener(
                View.OnTouchListener
                // si hay toques, llamamos al metodo hideSoftKeyboard
                { _, _ ->
                    if (activity != null) {
                        hideSoftKeyboard(activity)
                    }
                    false
                })
        }
        // lo mismo si estamos en una instancia de viewgroup
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                //setupUI(innerView);
            }
        }
    }

    // metodo para esconder el teclado, usando el InputMethodManager que es la funcionalidad nativa de accesibilidad
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }
}