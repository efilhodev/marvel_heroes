package br.com.efilhodev.marvel_heroes.feature.favorites.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.efilhodev.marvel_heroes.model.Character

class FavoritesListAdapter(
    private val onDetailClick: (Character) -> Unit,
    private val onFavoriteClick: (Character) -> Unit
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private val data = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val character = data[position]
        holder.bind(onFavoriteClick, onDetailClick, character)
    }

    fun submitList(characters: List<Character>) {
        data.clear()
        data.addAll(characters)
        notifyDataSetChanged()
    }
}