package com.brunoaybar.notekeeper.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.brunoaybar.notekeeper.model.Categoria
import com.brunoaybar.notekeeper.ui.adapters.CategoriesAdapter

class CategoriesView(context: Context): RecyclerView(context){


    private val categoriesAdapter: CategoriesAdapter = CategoriesAdapter()

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = categoriesAdapter
    }

    fun actualizar(categorias: List<Categoria>){
        categoriesAdapter.categorias = categorias
    }

}