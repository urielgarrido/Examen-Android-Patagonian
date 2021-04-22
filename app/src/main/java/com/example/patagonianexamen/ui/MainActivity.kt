package com.example.patagonianexamen.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.commit
import com.example.patagonianexamen.MainApplication
import com.example.patagonianexamen.R
import com.example.patagonianexamen.data.CancionEntityRoom
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
                SearchFragment.newInstance(application as MainApplication),
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
                LyricsFragment.newInstance(lyrics),
                LyricsFragment::class.java.simpleName
            )
        }
    }

    override fun goToHistorial() {
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(
                R.id.fragmentContainerView,
                HistoryFragment.newInstance(application as MainApplication),
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
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
    
    override fun onBackPressed() {
        showLoading(false)
        super.onBackPressed()
    }
}