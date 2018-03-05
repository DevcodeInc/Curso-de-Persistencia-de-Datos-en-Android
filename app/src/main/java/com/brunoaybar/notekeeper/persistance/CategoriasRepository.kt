package com.brunoaybar.notekeeper.persistance

import com.brunoaybar.notekeeper.model.Categoria

object CategoriasRepository {

    fun getCategorias() = listOf(
            Categoria(id = "1", nombre = "Android"),
            Categoria(id = "2", nombre = "Persistencia"),
            Categoria(id = "2", nombre = "Room"),
            Categoria(id = "2", nombre = "Files"),
            Categoria(id = "3", nombre = "Shared Preferences")
    )

}