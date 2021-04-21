package com.example.patagonianexamen.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.ui.adapter.CancionesAdapter

class HistoryFragment : BaseFragment() {

    private lateinit var historyRecyclerView: RecyclerView
    private val cancionesAdapter = CancionesAdapter(this::onCancionClicked)

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        configurarUI(view)
        actualizarTituloToolbar(getString(R.string.history_title_fragment))

        return view
    }

    private fun configurarUI(view: View) {
        historyRecyclerView = view.findViewById(R.id.history_recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        historyRecyclerView.layoutManager = layoutManager
        historyRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),layoutManager.orientation))
        historyRecyclerView.adapter = cancionesAdapter
    }

    private fun onCancionClicked(cancion: Cancion) {
        navigation?.goToLyrics(Lyrics("",""))
    }

}