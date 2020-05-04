package br.com.efilhodev.marvel_heroes.feature.base.view

import android.content.Context
import android.content.Intent
import android.os.Bundle

object Navigation {

    fun navigate(context: Context?, clazz: Class<*> , bundle: Bundle? = null) {
        val intent = Intent(context, clazz)
        bundle?.run {
            intent.putExtras(this)
        }
        context?.startActivity(intent)
    }
}