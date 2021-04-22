package com.example.patagonianexamen.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
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
import java.lang.Exception
import java.util.*


class SearchFragment(application: MainApplication) : BaseFragment() {

    private lateinit var noHayConexionTextView: TextView

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

    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    companion object {
        fun newInstance(application: MainApplication) = SearchFragment(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetDisponible()
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

    override fun onResume() {
        super.onResume()
        clearEditText()
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager?.unregisterNetworkCallback(networkCallback!!)
    }

    private fun clearEditText() {
        artistaEditText.text?.clear()
        cancionEditText.text?.clear()
    }

    private fun bindViewModel() {
        searchViewModel.ultimaCancionEntityRoom?.observe(viewLifecycleOwner, {
            cargarUltimaCancion(it)
        })

        searchViewModel.showLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        searchViewModel.isNetOn.observe(viewLifecycleOwner, {
            disableOrEnableButtons(it)
        })
    }

    private fun disableOrEnableButtons(online: Boolean) {
        if (online) {
            ultimoBuscadoRelativeLayout.isEnabled = true
            buscarButton.isEnabled = true
            noHayConexionTextView.visibility = View.GONE
        } else {
            ultimoBuscadoRelativeLayout.isEnabled = false
            buscarButton.isEnabled = false
            noHayConexionTextView.visibility = View.VISIBLE
        }
    }

    private fun cargarUltimaCancion(ultimaCancion: CancionEntityRoom?) {
        if (ultimaCancion != null) {
            ultimoBuscadoContainer.visibility = View.VISIBLE
            ultimoBuscadoArtistaTextView.text = ultimaCancion.artist.toUpperCase(Locale.getDefault())
            ultimoBuscadoCancionTextView.text = ultimaCancion.song.toUpperCase(Locale.getDefault())
        }
        ultimoBuscadoRelativeLayout.setOnClickListener {
            if (ultimaCancion != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val res =
                            searchViewModel.getLyricFromApi(ultimaCancion.artist, ultimaCancion.song)
                        if (res != null) {
                            activity?.runOnUiThread {
                                navigation?.goToLyrics(res)
                            }
                        }
                    }catch (exception: Exception){
                        activity?.runOnUiThread {
                            searchViewModel.setShowLoading(false)
                            mostrarAlertiDialogSinConexion()
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
        noHayConexionTextView = view.findViewById(R.id.no_hay_conexion_textView)
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
                try {
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
                }catch (exception: Exception){
                    activity?.runOnUiThread {
                        searchViewModel.setShowLoading(false)
                        mostrarAlertiDialogSinConexion()
                    }
                }

            }
        }
    }

    private fun mostrarAlertiDialogSinConexion(){
        noHayConexionTextView.visibility = View.VISIBLE

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

    private fun isNetDisponible() {
        connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        val request: NetworkRequest = NetworkRequest.Builder().build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                super.onLost(network)
                searchViewModel.isOnline(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                searchViewModel.isOnline(true)
            }
        }

        connectivityManager?.registerNetworkCallback(request, networkCallback!!)
    }

}