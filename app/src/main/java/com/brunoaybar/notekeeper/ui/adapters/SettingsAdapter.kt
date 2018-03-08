package com.brunoaybar.notekeeper.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlin.properties.Delegates

class SettingsAdapter: RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    companion object {
        val TYPE_SETTINGS_GRID = 1
        val TYPE_SETTINGS_TEXT_SIZE = 2
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        when(viewType){
            TYPE_SETTINGS_GRID -> ViewHolder
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val
    }

}