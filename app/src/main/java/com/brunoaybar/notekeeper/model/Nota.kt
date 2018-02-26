package com.brunoaybar.notekeeper.model

import java.util.Date

class Nota(val id: String,
           val contenido: String,
           val fechaCreacion: Date,
           val fechaModificacion: Date,
           val categorias: List<Categoria> = listOf())
