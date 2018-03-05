package com.brunoaybar.notekeeper.persistance

import com.brunoaybar.notekeeper.model.Nota
import java.util.*

object NotesRepository {

    private var notes = mutableListOf(
            createNote(id = "1",  contenido = getTestContent(30)),
            createNote(id = "2",  contenido = getTestContent(10)),
            createNote(id = "3",  contenido = getTestContent(50)),
            createNote(id = "4",  contenido = getTestContent(20)),
            createNote(id = "5",  contenido = getTestContent(40))
    )

    fun getNotas(): List<Nota> = notes

    fun getNota(id: String): Nota? {
        return getNotas().find { it.id == id }
    }

    fun agregar(id: String?, title: String?, contenido: String){
        if(id == null) {
            notes.add(createNote("1", title, contenido))
        }else {
            notes.forEachIndexed { index, nota ->
                if(nota.id == id) {
                    val newNote = nota.copy(titulo = title, contenido = contenido)
                    notes[index] = newNote
                }
            }
        }
    }

    private fun createNote(id: String, title: String? = null, contenido: String) =
            Nota(id, title, contenido, fechaCreacion = Date(), fechaModificacion = Date())


    private fun getTestContent(times: Int): String{
        return IntArray(times).map { "times" }.joinToString(separator = ", ")
    }
}