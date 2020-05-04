package br.com.efilhodev.marvel_heroes.feature.base.view

import android.os.Bundle
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.favorites.view.FavoritesFragment
import br.com.efilhodev.marvel_heroes.feature.home.view.HomeFragment
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_host)

        startViewPagerFragments()
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

    override fun onBackPressed() {
        if (host_view_pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            host_view_pager.currentItem = host_view_pager.currentItem - 1
        }
    }
}