package com.brunoaybar.notekeeper.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.model.Categoria

import kotlin.properties.Delegates

class CategoriesAdapter
    : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var categorias: List<Categoria> by Delegates.observable(listOf()){ _, _, _ ->
        notifyDataSetChanged()
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_category, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]

        holder.categoryTextView.text = categoria.nombre
        holder.checkImageView.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val checkImageView: ImageView = itemView.findViewById(R.id.checkImageView)


    }
}
