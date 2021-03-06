package com.brunoaybar.notekeeper.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.brunoaybar.notekeeper.R

import com.brunoaybar.notekeeper.model.Nota
import com.brunoaybar.notekeeper.ui.extensions.inflate
import kotlin.properties.Delegates

class NotesAdapter(var listener: Listener? = null)
    : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    interface Listener {
        fun onSelectNote(nota: Nota)
        fun onDownloadNote(nota: Nota)
        fun onDeleteNote(nota: Nota)
    }

    var notas: List<Nota> by Delegates.observable(listOf()){ _, _, _ ->
        notifyDataSetChanged()
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.item_nota, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nota = notas[position]

        holder.textoContenido.text = nota.contenido

        holder.botonDescargar.setOnClickListener {
            listener?.onDownloadNote(nota)
        }

        holder.botonEliminar.setOnClickListener {
            listener?.onDeleteNote(nota)
        }

        holder.itemView.setOnClickListener{
            listener?.onSelectNote(nota)
        }
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textoContenido: TextView = itemView.findViewById(R.id.contentTextView)
        val botonDescargar: ImageButton = itemView.findViewById(R.id.downloadButton)
        val botonEliminar: ImageButton = itemView.findViewById(R.id.deleteButton)

    }
}
