package com.example.patagonianexamen.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
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
import com.example.patagonianexamen.data.CancionEntityRoom
import com.example.patagonianexamen.viewmodels.SearchViewModel
import com.example.patagonianexamen.viewmodels.factory.SearchViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

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
        SearchViewModelFactory(application.repository)
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
        actualizarTituloToolbar(getString(R.string.search_title_fragment))
        bindViewModel()
        return view
    }

    private fun bindViewModel() {
        searchViewModel.ultimaCancionEntityRoom?.observe(viewLifecycleOwner, {
            cargarUltimaCancion(it)
        })

        searchViewModel.showLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun cargarUltimaCancion(ultimaCancion: CancionEntityRoom?) {
        if (ultimaCancion != null) {
            ultimoBuscadoContainer.visibility = View.VISIBLE
            ultimoBuscadoArtistaTextView.text = ultimaCancion.artist
            ultimoBuscadoCancionTextView.text = ultimaCancion.song
        }
        ultimoBuscadoRelativeLayout.setOnClickListener {
            if (ultimaCancion != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val res =
                        searchViewModel.getLyricFromApi(ultimaCancion.artist, ultimaCancion.song)
                    if (res != null) {
                        activity?.runOnUiThread {
                            navigation?.goToLyrics(res)
                        }
                    }
                }
            }
        }

        verHistorialButton.setOnClickListener {
            navigation?.goToHistorial()
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
            val artista = artistaEditText.text.toString()
            val song = cancionEditText.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val lyrics = searchViewModel.getLyricFromApi(artista, song)
                if (lyrics != null) {
                    searchViewModel.insertCancionRoom(
                        CancionEntityRoom(
                            artist = artista,
                            song = song
                        )
                    )
                    activity?.runOnUiThread {
                        navigation?.goToLyrics(lyrics)
                    }
                } else {
                    activity?.runOnUiThread {
                        mostrarAlertDialogError()
                    }
                }

            }
        }
    }

    private fun mostrarAlertDialogError() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.letra_no_encontrada))
            .setMessage("No lyrics found")
            .setPositiveButton(
                getString(R.string.aceptar)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}