package br.com.efilhodev.marvel_heroes.feature.base.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.efilhodev.marvel_heroes.feature.base.gateway.ConnectivityReceiver
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {
    private var mSnackBar: Snackbar? = null

    abstract fun onNetworkConnectionChangedStatus(isConnected: Boolean)

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        onNetworkConnectionChangedStatus(isConnected)
    }

    fun showErrorSnackBar(view: View, error: String) {
        mSnackBar = Snackbar.make(view, error, Snackbar.LENGTH_LONG)
        mSnackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
        mSnackBar?.show()
    }

    fun hideErrorSnackBar() {
        mSnackBar?.dismiss()
    }
}
