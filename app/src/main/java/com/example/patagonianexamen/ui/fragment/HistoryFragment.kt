package com.example.patagonianexamen.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patagonianexamen.MainApplication
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.ui.adapter.CancionesAdapter
import com.example.patagonianexamen.viewmodels.HistoryViewModel
import com.example.patagonianexamen.viewmodels.factory.HistoryViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HistoryFragment(application: MainApplication) : BaseFragment() {

    private lateinit var historyRecyclerView: RecyclerView
    private val cancionesAdapter = CancionesAdapter(this::onCancionClicked)

    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(application.repository)
    }

    companion object {
        fun newInstance(application: MainApplication) = HistoryFragment(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shouldShowBackIcon = true
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        bindViewModel()
    }

    private fun bindViewModel() {
        historyViewModel.listCancionesFromRoom?.observe(viewLifecycleOwner, {
            val canciones: MutableList<Cancion> = ArrayList()
            for (cancionEntityRoom in it) {
                canciones.add(cancionEntityRoom.convertToCancionClass())
            }
            actualizarCanciones(canciones)
        })

        historyViewModel.showLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun actualizarCanciones(canciones: MutableList<Cancion>) {
        cancionesAdapter.actualizarListaCanciones(canciones)
    }

    private fun setupAdapter() {
        historyRecyclerView.adapter = cancionesAdapter
    }

    private fun configurarUI(view: View) {
        historyRecyclerView = view.findViewById(R.id.history_recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        historyRecyclerView.layoutManager = layoutManager
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )
    }

    private fun mostrarAlertiDialogSinConexion(){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.sin_conexion))
            .setMessage(getString(R.string.no_hay_internet))
            .setPositiveButton(
                getString(R.string.aceptar)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onCancionClicked(cancion: Cancion) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val lyricRes = historyViewModel.getLyricFromApi(cancion.artist, cancion.song)
                if (lyricRes != null) {
                    activity?.runOnUiThread {
                        navigation?.goToLyrics(lyricRes)
                    }
                }
            }catch (exception: Exception){
                activity?.runOnUiThread {
                    historyViewModel.setShowLoading(false)
                    mostrarAlertiDialogSinConexion()
                }
            }

        }
    }

}