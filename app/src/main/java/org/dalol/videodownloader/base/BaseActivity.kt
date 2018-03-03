package org.dalol.videodownloader.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by filippo on 21/01/2018.
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        onActivityCreated(savedInstanceState)
    }

    open fun onActivityCreated(savedInstanceState: Bundle?) {}

    open fun showToast(message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    abstract fun getContentView() : Int
}