package com.example.patagonianexamen.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.patagonianexamen.R

class LyricsFragment : Fragment() {

    private lateinit var lyricsTextView: TextView

    companion object {
        fun newInstance() = LyricsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lyrics, container, false)
        configurarUI(view)
        return view
    }

    private fun configurarUI(view: View) {
        lyricsTextView = view.findViewById(R.id.lyrics_textView)
    }
}