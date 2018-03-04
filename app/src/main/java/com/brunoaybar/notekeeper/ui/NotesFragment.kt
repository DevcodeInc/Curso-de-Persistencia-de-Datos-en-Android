package com.brunoaybar.notekeeper.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.brunoaybar.notekeeper.R
import com.brunoaybar.notekeeper.model.Nota
import com.brunoaybar.notekeeper.ui.adapters.NotesAdapter
import kotlinx.android.synthetic.main.fragment_notes.*


/**
 * A simple [Fragment] subclass.
 */
class NotesFragment : Fragment() {

    companion object {
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }

    private lateinit var adapter: NotesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NotesAdapter()
        notasRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        notasRecyclerView.adapter = adapter
    }

    fun actualizar(notes: List<Nota>){
        adapter.notas = notes
    }

}// Required empty public constructor
