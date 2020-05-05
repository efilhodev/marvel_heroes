package br.com.efilhodev.marvel_heroes.feature.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.model.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_list_character.view.*

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        onFavoriteClick: (Character, Boolean) -> Unit,
        onDetailClick: (Character) -> Unit?,
        character: Character?,
        isFavorite: Boolean
    ) {
        character?.run {
            itemView.item_list_character_name.text = this.name
            itemView.item_list_character_favorite_image.setOnClickListener { onFavoriteClick(character, isFavorite) }
            itemView.card_view.setOnClickListener { onDetailClick(character) }

            showCharacterImage()

            changeFavoriteIconStatus(isFavorite)
        }
    }

    private fun changeFavoriteIconStatus(isFavorite: Boolean) {
        if (isFavorite) itemView.item_list_character_favorite_image.setImageResource(R.drawable.ic_favorite_24dp)
        else itemView.item_list_character_favorite_image.setImageResource(R.drawable.ic_favorite_border_24dp)
    }

    private fun Character.showCharacterImage() {
        Glide.with(itemView)
            .load(thumbnail.getPortraitPath())
            .placeholder(R.mipmap.ph_marvel)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemView.item_list_character_image)
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_character, parent, false)
            return CharacterViewHolder(view)
        }
    }
}