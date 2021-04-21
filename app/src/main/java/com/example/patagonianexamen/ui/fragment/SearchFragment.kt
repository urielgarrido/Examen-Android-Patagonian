package com.example.patagonianexamen.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import com.example.patagonianexamen.MainApplication
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.viewModel.SearchViewModel
import com.example.patagonianexamen.viewModel.factory.SearchViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class SearchFragment(application: MainApplication) : BaseFragment() {

    private lateinit var artistaEditText: TextInputEditText
    private lateinit var cancionEditText: TextInputEditText
    private lateinit var buscarButton: Button

    private lateinit var ultimoBuscadoContainer: CardView
    private lateinit var ultimoBuscadoRelativeLayout: RelativeLayout
    private lateinit var ultimoBuscadoArtistaTextView: TextView
    private lateinit var ultimoBuscadoCancionTextView: TextView
    private lateinit var verHistorialButton: Button

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(application.repository )
    }

    companion object {
        fun newInstance(application: MainApplication) = SearchFragment(application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)

        configurarUI(view)
        bindViewModel()
        return view
    }

    private fun bindViewModel() {
        searchViewModel.ultimaCancion?.observe(viewLifecycleOwner, {
            cargarUltimaCancion(it)
        })
    }

    private fun cargarUltimaCancion(ultimaCancion: Cancion?) {
        ultimoBuscadoContainer.visibility = View.VISIBLE
        if (ultimaCancion != null) {
            ultimoBuscadoArtistaTextView.text = ultimaCancion.artist
            ultimoBuscadoCancionTextView.text = ultimaCancion.song
        }
        ultimoBuscadoRelativeLayout.setOnClickListener {
            if (ultimaCancion != null) {
               val res = searchViewModel.getLyricFromApi(ultimaCancion.artist, ultimaCancion.song)

                if (res != null){
                    navigation?.goToLyrics(res)
                }
            }
        }

        verHistorialButton.setOnClickListener {

        }
    }

    private fun configurarUI(view: View) {
        artistaEditText = view.findViewById(R.id.artistaEditText)
        cancionEditText = view.findViewById(R.id.cancionEditText)
        buscarButton = view.findViewById(R.id.buscar_button)
        ultimoBuscadoContainer = view.findViewById(R.id.ultimo_buscado_container)
        ultimoBuscadoRelativeLayout = view.findViewById(R.id.ultimo_buscado_relativeLayout)
        ultimoBuscadoArtistaTextView = view.findViewById(R.id.artista_ultimo_buscado_textView)
        ultimoBuscadoCancionTextView = view.findViewById(R.id.cancion_ultimo_buscado_textView)
        verHistorialButton = view.findViewById(R.id.ver_historial_button)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        buscarButton.setOnClickListener {

        }
    }

}