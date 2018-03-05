package com.brunoaybar.notekeeper.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.brunoaybar.notekeeper.model.Nota
import com.brunoaybar.notekeeper.ui.adapters.NotesAdapter
import com.brunoaybar.notekeeper.ui.extensions.isTablet

open class NotesView(context: Context): RecyclerView(context){

    private val notesAdapter: NotesAdapter = NotesAdapter()

    var listener: NotesAdapter.Listener? = null
    set(value) { notesAdapter.listener = value }

    init {
        layoutManager = StaggeredGridLayoutManager(getSpanCount(), LinearLayoutManager.VERTICAL)
        adapter = notesAdapter
    }

    fun actualizar(notas: List<Nota>){
        notesAdapter.notas = notas
    }

    private fun getSpanCount() = if(context.isTablet()) 3 else 2

}