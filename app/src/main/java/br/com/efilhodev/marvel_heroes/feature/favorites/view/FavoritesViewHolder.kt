package br.com.efilhodev.marvel_heroes.feature.favorites.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.model.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_list_favorite_character.view.*

class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        onFavoriteClick: (Character) -> Unit,
        onDetailClick: (Character) -> Unit,
        character: Character?
    ) {
        character?.run {
            itemView.card_view.setOnClickListener { onDetailClick(character) }
            itemView.item_list_favorite_character_name.text = this.name
            itemView.item_list_favorite_character_favorite_image.setOnClickListener {
                onFavoriteClick(character)
            }

            showCharacterImage()
        }
    }

    private fun Character.showCharacterImage() {
        Glide.with(itemView)
            .load(thumbnail.getPortraitPath())
            .placeholder(R.mipmap.ph_marvel)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemView.item_list_favorite_character_image)
    }

    companion object {
        fun create(parent: ViewGroup): FavoritesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_favorite_character, parent, false)
            return FavoritesViewHolder(view)
        }
    }
}