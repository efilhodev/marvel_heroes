package br.com.efilhodev.marvel_heroes.feature.base.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.base.gateway.ConnectivityReceiver
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    internal lateinit var viewmodelFactory: ViewModelProvider.Factory

    private var viewmodel: BaseViewModel? = null

    private var mSnackBar: Snackbar? = null

    abstract fun initViewModel(): BaseViewModel?

    abstract fun onNetworkConnectionChangedStatus(isConnected: Boolean)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewmodel = initViewModel()

        observeErrorMessage()
    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        onNetworkConnectionChangedStatus(isConnected)
    }

    private fun observeErrorMessage() {
        viewmodel?.errorMessage?.observe(this, Observer { message -> showErrorMessage(message) })
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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
