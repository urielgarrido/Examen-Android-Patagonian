package com.example.patagonianexamen.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.patagonianexamen.R
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var artistaEditText: TextInputEditText
    private lateinit var cancionEditText: TextInputEditText
    private lateinit var buscarButton: Button

    private lateinit var ultimoBuscadoContainer: CardView
    private lateinit var ultimoBuscadoRelativeLayout: LinearLayout
    private lateinit var ultimoBuscadoArtistaTextView: TextView
    private lateinit var ultimoBuscadoCancionTextView: TextView
    private lateinit var verHistorialButton: Button

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)

        configurarUI(view)

        return view
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

        ultimoBuscadoRelativeLayout.setOnClickListener {

        }

        verHistorialButton.setOnClickListener {

        }
    }

}