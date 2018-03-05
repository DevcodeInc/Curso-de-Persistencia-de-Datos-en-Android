package com.brunoaybar.notekeeper.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.model.Categoria
import kotlin.properties.Delegates

class SelectableCategory(val categoria: Categoria,
                         var isSelected: Boolean)

class SelectCategoriesAdapter
    : RecyclerView.Adapter<SelectCategoriesAdapter.ViewHolder>() {

    var categorias: List<SelectableCategory> by Delegates.observable(listOf()){ _, _, _ ->
        notifyDataSetChanged()
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_category, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.bind(categoria)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        private val checkImageView: ImageView = itemView.findViewById(R.id.checkImageView)

        fun bind(categoria: SelectableCategory){
            categoryTextView.text = categoria.categoria.nombre
            checkImageView.setImageDrawable(getDrawableForState(categoria.isSelected))

            itemView.setOnClickListener{
                categoria.isSelected = !categoria.isSelected
                bind(categoria)
            }
        }

        private fun getDrawableForState(selected: Boolean): Drawable? {
            return ContextCompat.getDrawable(context, when(selected){
                true -> R.drawable.ic_check_selected
                false -> R.drawable.ic_check_unselected
            })
        }

    }
}
