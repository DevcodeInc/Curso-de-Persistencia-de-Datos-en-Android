package com.brunoaybar.notekeeper.ui

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.model.Nota
import com.brunoaybar.notekeeper.persistance.CategoriasRepository
import com.brunoaybar.notekeeper.persistance.NotesRepository
import com.brunoaybar.notekeeper.ui.adapters.SelectCategoriesAdapter
import com.brunoaybar.notekeeper.ui.adapters.SelectableCategory
import com.brunoaybar.notekeeper.ui.extensions.getSafeColor
import com.brunoaybar.notekeeper.ui.extensions.hideKeyboard
import com.brunoaybar.notekeeper.ui.extensions.listenKeyboardChanges
import com.brunoaybar.notekeeper.ui.extensions.start
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    companion object {
        private val PARAM_NOTE_ID = "param_note_id"

        fun start(activity: Activity, nota: Nota? = null){
            val bundle = Bundle().apply {
                putString(PARAM_NOTE_ID, nota?.id)
            }
            activity.start<NoteActivity>(bundle)
        }
    }

    private var nota: Nota? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        nota = restaurarNota(savedInstanceState ?: intent?.extras)
        mostrar(nota)

        title = ""
        setupCategories()
    }

    private fun guardar(){
        NotesRepository.agregar(
                id = nota?.id,
                title = titleEditText.text.toString(),
                contenido = contentEditText.text.toString())
    }

    private fun restaurarNota(bundle: Bundle?): Nota?{
        val id = bundle?.getString(PARAM_NOTE_ID) ?: return null
        return NotesRepository.getNota(id)
    }

    private fun mostrar(nota: Nota?){
        titleEditText.setText(nota?.titulo ?: "")
        contentEditText.setText(nota?.contenido ?: "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_categories -> {
                mostrarCategorias()
                true
            }
            R.id.action_save -> {
                guardar()
                finish()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarCategorias(){
        hideKeyboard()
        Handler().postDelayed({
            val behavior = BottomSheetBehavior.from(bottom_sheet)
            when(behavior.state){
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottom_sheet.visibility = View.VISIBLE
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    updateBackgroundColor(withShadow = true)
                }
                else -> {
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    updateBackgroundColor(withShadow = false)
                }
            }

        }, 200)
    }

    private fun setupCategories() {
        bottom_sheet.layoutManager = LinearLayoutManager(this)
        bottom_sheet.adapter = SelectCategoriesAdapter().apply {
            categorias = CategoriasRepository.getCategorias().map { SelectableCategory(it, false) }
        }

        mainView.listenKeyboardChanges(keyboardOpened = {
            updateBackgroundColor(withShadow = false)
            bottom_sheet.visibility = View.GONE
            BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_COLLAPSED
        })
    }

    private fun updateBackgroundColor(withShadow: Boolean){
        val color = getSafeColor(when(withShadow){
            true -> R.color.background_shadowed
            false -> R.color.background_white
        })
        mainView.setBackgroundColor(color)
    }
}
