package com.brunoaybar.notekeeper.model

import java.util.Date

data class Nota(val id: String,
                val titulo: String?,
                val contenido: String,
                val fechaCreacion: Date,
                val fechaModificacion: Date,
                val categorias: List<Categoria> = listOf())
