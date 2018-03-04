package com.brunoaybar.notekeeper.persistance

import com.brunoaybar.notekeeper.model.Categoria

object CategoriasRepository {

    fun getCategorias() = listOf(
            Categoria(id = "1", nombre = "Importante"),
            Categoria(id = "2", nombre = "Personal"),
            Categoria(id = "3", nombre = "Educación")
    )

}