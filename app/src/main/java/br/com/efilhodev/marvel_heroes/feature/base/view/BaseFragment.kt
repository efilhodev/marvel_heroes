package br.com.efilhodev.marvel_heroes.feature.base.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var viewmodelFactory: ViewModelProvider.Factory

    private lateinit var viewmodel: BaseViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = initViewModel()
        observeErrorMessage()
    }

    abstract fun initViewModel(): BaseViewModel

    private fun observeErrorMessage() {
        viewmodel.errorMessage.observe(this, Observer { message -> showErrorMessage(message) })
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}