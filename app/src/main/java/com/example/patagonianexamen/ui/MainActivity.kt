package com.example.patagonianexamen.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.commit
import com.example.patagonianexamen.MainApplication
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.navigation.MainNavigator
import com.example.patagonianexamen.ui.fragment.BaseFragment
import com.example.patagonianexamen.ui.fragment.HistoryFragment
import com.example.patagonianexamen.ui.fragment.LyricsFragment
import com.example.patagonianexamen.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity(), BaseFragment.BaseViewListener, MainNavigator {

    private lateinit var layoutLoaders: FrameLayout
    private lateinit var fragmentContainerView: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configurarUI()
        if (savedInstanceState == null) {
            goToSearchFragment()
        }
    }

    private fun configurarUI() {
        layoutLoaders = findViewById(R.id.layout_loaders)
        fragmentContainerView = findViewById(R.id.fragmentContainerView)
    }

    override fun goToSearchFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragmentContainerView,
                SearchFragment.newInstance(MainApplication()),
                SearchFragment::class.java.simpleName
            )
        }
    }

    override fun goToLyrics(lyrics: Lyrics) {
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(
                R.id.fragmentContainerView,
                LyricsFragment.newInstance(),
                LyricsFragment::class.java.simpleName
            )
        }
    }

    override fun goToHistorial(listaCanciones: List<Cancion>) {
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(
                R.id.fragmentContainerView,
                HistoryFragment.newInstance(),
                HistoryFragment::class.java.simpleName
            )
        }
    }

    override fun showLoading(showLoading: Boolean) {
        layoutLoaders.visibility = if (showLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun showBackIcon(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    override fun updateToolbarTitle(title: String) {
        TODO("Not yet implemented")
    }
    
    override fun onBackPressed() {
        showLoading(false)
        super.onBackPressed()
    }
}