package com.brunoaybar.notekeeper.persistance

import com.brunoaybar.notekeeper.model.Nota
import java.util.*

object NotesRepository {

    private var notes = mutableListOf(
            Nota(id = "1", contenido = getTestContent(50), fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "2", contenido = getTestContent(30), fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "3", contenido = getTestContent(10), fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "4", contenido = getTestContent(50), fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "5", contenido = getTestContent(20), fechaCreacion = Date(), fechaModificacion = Date()),
            Nota(id = "6", contenido = getTestContent(40), fechaCreacion = Date(), fechaModificacion = Date())
    )

    fun getNotas(): List<Nota> = notes

    fun agregar(nota: String){
        notes.add(Nota("1", nota, Date(), Date()))
    }


    private fun getTestContent(times: Int): String{
        return IntArray(times).map { "times" }.joinToString(separator = ", ")
    }
}