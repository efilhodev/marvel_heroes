package br.com.efilhodev.marvel_heroes.feature.base.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.details.view.CharacterDetailActivity
import br.com.efilhodev.marvel_heroes.feature.favorites.view.FavoritesFragment
import br.com.efilhodev.marvel_heroes.feature.home.view.HomeFragment
import br.com.efilhodev.marvel_heroes.feature.search.view.SearchActivity
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_host)

        startViewPagerFragments()
        setupSearchView()
    }

    private fun startViewPagerFragments() {
        val adapter = ScreenSlidePageAdapter(supportFragmentManager)
        host_view_pager.adapter = adapter
        host_tab_layout.setupWithViewPager(host_view_pager)

        adapter.addFragment(HomeFragment(), getString(R.string.characters))
        adapter.addFragment(FavoritesFragment(), getString(R.string.favorites))
    }

    override fun initViewModel(): BaseViewModel? {
        return null
    }

    override fun onNetworkConnectionChangedStatus(isConnected: Boolean) {
        if (isConnected.not()) showErrorSnackBar(host_root, getString(R.string.error_connection))
        else hideErrorSnackBar()
    }

    private fun setupSearchView() {
        host_search_view.setOnClickListener { onSearchClick() }
        host_search_close_view.setOnClickListener { onCloseSearchClick() }
    }

    private fun onSearchClick() {
        if (host_search_edit_view.isVisible.not()) {
            host_search_close_view.visibility = View.VISIBLE
            host_search_edit_view.visibility = View.VISIBLE
            host_search_edit_view.requestFocus()
            showKeyboard()
        } else {
            val query = host_search_edit_view.text.toString()
            if (query.isBlank().not()) navigateToSearchActivity(query)
        }
    }

    private fun onCloseSearchClick() {
        hideKeyboard(host_root)
        host_search_close_view.visibility = View.GONE
        host_search_edit_view.visibility = View.GONE
        host_search_edit_view.setText("")
    }

    private fun navigateToSearchActivity(name: String) {
        val bundle = Bundle()
        bundle.putString(SearchActivity.QUERY_NAME_ARG, name)

        Navigation.navigate(this, SearchActivity::class.java, bundle)
    }

    override fun onBackPressed() {
        if (host_view_pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            host_view_pager.currentItem = host_view_pager.currentItem - 1
        }
    }
}