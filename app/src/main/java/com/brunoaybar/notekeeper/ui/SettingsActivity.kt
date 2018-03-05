package com.brunoaybar.notekeeper.ui

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.ui.extensions.start

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun start(activity: Activity){
            activity.start<SettingsActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }



}
