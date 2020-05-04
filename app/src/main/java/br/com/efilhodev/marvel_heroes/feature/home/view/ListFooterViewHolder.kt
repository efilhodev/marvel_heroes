package br.com.efilhodev.marvel_heroes.feature.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.home.gateway.DataSourceState
import kotlinx.android.synthetic.main.item_list_footer.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(onRetryClick: () -> Unit?, state: DataSourceState?) {
        itemView.item_list_footer_error_text_view.setOnClickListener { onRetryClick() }

        when (state) {
            DataSourceState.LOADING -> {
                showLoadingState()
            }
            DataSourceState.ERROR -> {
                showErrorState()
            }
            DataSourceState.DONE -> {
                showDoneState()
            }
        }
    }

    private fun showErrorState() {
        itemView.item_list_footer_progress_bar.visibility = View.GONE
        itemView.item_list_footer_error_text_view.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        itemView.item_list_footer_progress_bar.visibility = View.VISIBLE
        itemView.item_list_footer_error_text_view.visibility = View.GONE
    }

    private fun showDoneState() {
        itemView.item_list_footer_progress_bar.visibility = View.GONE
        itemView.item_list_footer_error_text_view.visibility = View.GONE
    }

    companion object {
        fun create(parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            return ListFooterViewHolder(view)
        }
    }
}