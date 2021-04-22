package com.example.patagonianexamen.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.Cancion
import java.util.*
import kotlin.collections.ArrayList

class CancionesAdapter(
    val listener: CancionClickListener): RecyclerView.Adapter<CancionesAdapter.CancionesViewHolder>() {

    private var canciones = ArrayList<Cancion>()

    class CancionesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistTextView: TextView = itemView.findViewById(R.id.artist_textView)
        val cancionTextView: TextView = itemView.findViewById(R.id.cancion_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionesViewHolder {
        return CancionesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cancion, parent,false)
        ).apply {
            itemView.setOnClickListener { listener.onClick(canciones[adapterPosition]) }
        }
    }

    override fun onBindViewHolder(holder: CancionesViewHolder, position: Int) {
        val cancion: Cancion = canciones[position]
        holder.artistTextView.text = cancion.artist.toUpperCase(Locale.getDefault())
        holder.cancionTextView.text = cancion.song.toUpperCase(Locale.getDefault())
    }

    override fun getItemCount(): Int {
       return canciones.size
    }

    fun actualizarListaCanciones(canciones: MutableList<Cancion>) {
        this.canciones.apply {
            clear()
            addAll(canciones)
        }
        notifyDataSetChanged()
    }

    fun interface CancionClickListener {
        fun onClick(cancion: Cancion)
    }
}