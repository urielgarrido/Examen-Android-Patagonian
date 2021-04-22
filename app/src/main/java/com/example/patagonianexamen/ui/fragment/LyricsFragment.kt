package com.example.patagonianexamen.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.Lyrics

class LyricsFragment : BaseFragment() {

    private lateinit var lyricsTextView: TextView

    private var lyrics: Lyrics? = null

    companion object {
        private const val LYRICS = "Lyrics"

        fun newInstance(lyrics: Lyrics): LyricsFragment {
            return LyricsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LYRICS, lyrics)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lyrics = arguments?.getParcelable(LYRICS)
        shouldShowBackIcon = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lyrics, container, false)
        configurarUI(view)
        actualizarTituloToolbar(getString(R.string.lyrics_title_fragment))
        actualizarUI()
        return view
    }

    private fun configurarUI(view: View) {
        lyricsTextView = view.findViewById(R.id.lyrics_textView)
    }

    private fun actualizarUI() {
        lyrics?.let {
            lyricsTextView.text = it.lyrics
        }
    }
}