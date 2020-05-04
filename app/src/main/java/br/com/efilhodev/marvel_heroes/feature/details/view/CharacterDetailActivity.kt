package br.com.efilhodev.marvel_heroes.feature.details.view

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.base.view.BaseActivity
import br.com.efilhodev.marvel_heroes.feature.details.gateway.CharacterDetailViewModel
import br.com.efilhodev.marvel_heroes.model.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.character_detail_activity.*
import kotlinx.android.synthetic.main.item_list_character.view.*

class CharacterDetailActivity : BaseActivity() {

    private lateinit var viewmodel: CharacterDetailViewModel
    private var character: Character? = null
    private var isFavorite: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail_activity)

        character = getCharacterArg()
        setupViews()
        observeFavoriteIds()
    }

    override fun initViewModel(): BaseViewModel {
        viewmodel = viewmodelFactory.create(CharacterDetailViewModel::class.java)
        return viewmodel
    }

    override fun onNetworkConnectionChangedStatus(isConnected: Boolean) {
        if (isConnected.not()) showErrorSnackBar(host_root, getString(R.string.error_connection))
        else hideErrorSnackBar()
    }

    private fun getCharacterArg(): Character? {
        return intent.getSerializableExtra(CHARACTER_ARG) as Character?
    }

    private fun setupViews() {
        setCollapseToolbarTypeface()
        setCharacterImage()
        setCharacterInfo()
        setFavoriteButtonBehaviour()
    }

    private fun setCollapseToolbarTypeface() {
        character_detail_collapsing_toolbar_layout.setCollapsedTitleTypeface(
            ResourcesCompat.getFont(
                this,
                R.font.bangers
            )
        )
        character_detail_collapsing_toolbar_layout.setExpandedTitleTypeface(
            ResourcesCompat.getFont(
                this,
                R.font.bangers
            )
        )
    }

    private fun setCharacterImage() {
        Glide.with(this)
            .load(character?.thumbnail?.getLandscapePath())
            .placeholder(R.mipmap.ph_marvel)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(character_detail_image_view)
    }

    private fun setCharacterInfo() {
        character?.apply {
            character_detail_toolbar.title = name
            character_detail_description_text_view.text = getCharacterDescription()
        }
    }

    private fun setFavoriteButtonBehaviour() {
        character_detail_favorite_image.setOnClickListener {
            viewmodel.setOrRemoveFavoriteCharacter(character, isFavorite)
        }
    }

    private fun Character.getCharacterDescription() =
        if (description.isBlank().not()) description else getString(R.string.character_detail_no_description)

    private fun observeFavoriteIds() {
        viewmodel.favoritesIds.observe(this, Observer {
            isFavorite = it.contains(character?.id)
            changeFavoriteIconStatus(isFavorite)
        })
    }

    private fun changeFavoriteIconStatus(isFavorite: Boolean?) {
        isFavorite?.apply {
            if (this) character_detail_favorite_image.setImageResource(R.drawable.ic_favorite_24dp)
            else character_detail_favorite_image.setImageResource(R.drawable.ic_favorite_border_24dp)
        }
    }

    companion object {
        const val CHARACTER_ARG = "CHARACTER_ARG"
    }
}