package com.brunoaybar.notekeeper.ui

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.ui.extensions.start
import kotlinx.android.synthetic.main.activity_settings.*
import com.afollestad.materialdialogs.MaterialDialog
import android.R.array
import android.view.View
import android.text.InputType
import android.view.MenuItem
import com.brunoaybar.notekeeper.persistance.SettingsRepository

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun start(activity: Activity){
            activity.start<SettingsActivity>()
        }
    }

    private var name: String? = null
    private var selectedStyleIndex = 0
    private var repo = SettingsRepository.getInstance()

    private fun loadValues(){
        name = repo.getSavedName(applicationContext);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        settingsNameView.setOnClickListener {
            openNameDialog()
        }

        settingsStyleView.setOnClickListener {
            openStyleDialog()
        }

        loadValues()

    }

    private fun openNameDialog(){
        MaterialDialog.Builder(this)
                .title(R.string.settings_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(getString(R.string.settings_name_hint), name, { dialog, input ->
                    // Do something
                    repo.saveName(applicationContext, input.toString())
                    loadValues()
                }).show()
    }

    private fun openStyleDialog(){
        MaterialDialog.Builder(this)
                .title(R.string.settings_notes_ui)
                .items(R.array.settings_notes_styles)
                .itemsCallbackSingleChoice(selectedStyleIndex) { dialog, view, which, text ->
                    /**
                     * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                     * returning false here won't allow the newly selected radio button to actually be selected.
                     */
                    /**
                     * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                     * returning false here won't allow the newly selected radio button to actually be selected.
                     */
                    true
                }
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
