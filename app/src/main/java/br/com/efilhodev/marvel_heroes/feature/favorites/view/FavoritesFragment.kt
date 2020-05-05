package br.com.efilhodev.marvel_heroes.feature.favorites.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.base.view.BaseFragment
import br.com.efilhodev.marvel_heroes.feature.base.view.Navigation
import br.com.efilhodev.marvel_heroes.feature.details.view.CharacterDetailActivity
import br.com.efilhodev.marvel_heroes.feature.favorites.gateway.FavoritesViewModel
import br.com.efilhodev.marvel_heroes.feature.home.view.HomeFragment
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment() {

    private lateinit var viewmodel: FavoritesViewModel
    private lateinit var adapter: FavoritesListAdapter

    override fun initViewModel(): BaseViewModel {
        viewmodel = viewmodelFactory.create(FavoritesViewModel::class.java)
        return viewmodel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCharacterListAdapter()
    }

    private fun startCharacterListAdapter() {
        adapter = FavoritesListAdapter(
            onFavoriteClick = { viewmodel.removeFavoriteCharacter(it) },
            onDetailClick = { navigateToCharacterDetailActivity(it) }
        )

        favorites_recycler_view.layoutManager =
            StaggeredGridLayoutManager(HomeFragment.DEFAULT_SPAN_COUNT, RecyclerView.VERTICAL)
        favorites_recycler_view.adapter = adapter

        observeFavoritesCharacters()
    }

    private fun navigateToCharacterDetailActivity(character: Character) {
        val bundle = Bundle()
        bundle.putSerializable(CharacterDetailActivity.CHARACTER_ARG, character)

        Navigation.navigate(context, CharacterDetailActivity::class.java, bundle)
    }

    private fun observeFavoritesCharacters() {
        viewmodel.favoritesCharacters.observe(
            this,
            Observer { characters ->
                verifyEmptyList(characters)
                adapter.submitList(characters)
            })
    }

    private fun verifyEmptyList(characters: List<Character>) {
        if (characters.isNotEmpty()) hideEmptyListView()
        else showEmptyListView()
    }

    private fun showEmptyListView() {
        favorites_empty_list_text_view.visibility = View.VISIBLE
    }

    private fun hideEmptyListView() {
        favorites_empty_list_text_view.visibility = View.GONE
    }
}