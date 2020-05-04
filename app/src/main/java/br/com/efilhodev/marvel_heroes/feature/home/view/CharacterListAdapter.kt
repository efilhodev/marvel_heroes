package br.com.efilhodev.marvel_heroes.feature.home.view

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.efilhodev.marvel_heroes.feature.home.gateway.DataSourceState
import br.com.efilhodev.marvel_heroes.model.Character

class CharacterListAdapter(
    private val onRetryClick: () -> Unit,
    private val onFavoriteClick: (Character, Boolean) -> Unit,
    private val onDetailClick: (Character) -> Unit,
    private var favorites: List<Int>
) :
    PagedListAdapter<Character, RecyclerView.ViewHolder>(characterDiffCallback) {

    private var state = DataSourceState.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) CharacterViewHolder.create(parent)
        else ListFooterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {

            val character = getItem(position)
            val isFavorite = favorites.contains(character?.id)

            (holder as CharacterViewHolder).bind(
                onFavoriteClick,
                onDetailClick,
                character,
                isFavorite
            )
        } else {
            (holder as ListFooterViewHolder).bind(onRetryClick, state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == DataSourceState.LOADING || state == DataSourceState.ERROR)
    }

    fun setState(state: DataSourceState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    fun updateFavorites(favorites: List<Int>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }

    companion object {
        private const val DATA_VIEW_TYPE = 1
        private const val FOOTER_VIEW_TYPE = 2

        val characterDiffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}