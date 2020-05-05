package br.com.efilhodev.marvel_heroes.feature.search.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.efilhodev.marvel_heroes.feature.home.view.CharacterViewHolder
import br.com.efilhodev.marvel_heroes.model.Character

class SearchListAdapter(
    private val onDetailClick: (Character) -> Unit,
    private val onFavoriteClick: (Character, Boolean) -> Unit,
    private var favorites: List<Int>
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private val data = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = data[position]
        val isFavorite = favorites.contains(character.id)
        holder.bind(onFavoriteClick, onDetailClick, character, isFavorite)
    }

    fun submitList(characters: List<Character>) {
        data.clear()
        data.addAll(characters)
        notifyDataSetChanged()
    }

    fun updateFavorites(favorites: List<Int>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }
}