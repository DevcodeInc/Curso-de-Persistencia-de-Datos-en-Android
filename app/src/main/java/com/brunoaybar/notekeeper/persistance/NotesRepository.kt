package com.brunoaybar.notekeeper.persistance

import com.brunoaybar.notekeeper.model.Nota
import java.util.*

object NotesRepository {

    private var notes = mutableListOf(
            Nota(id = "1", contenido = "Test test test test", fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "2", contenido = "Test test test", fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "3", contenido = "Test test", fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "4", contenido = "Test", fechaCreacion = Date(), fechaModificacion = Date())
    )

    fun getNotas(): List<Nota> = notes

    fun agregar(nota: String){
        notes.add(Nota("1", nota, Date(), Date()))
    }

}