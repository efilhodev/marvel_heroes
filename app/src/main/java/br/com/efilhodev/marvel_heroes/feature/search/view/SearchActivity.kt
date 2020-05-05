package br.com.efilhodev.marvel_heroes.feature.search.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.base.view.BaseActivity
import br.com.efilhodev.marvel_heroes.feature.base.view.Navigation
import br.com.efilhodev.marvel_heroes.feature.details.view.CharacterDetailActivity
import br.com.efilhodev.marvel_heroes.feature.home.gateway.DataSourceState
import br.com.efilhodev.marvel_heroes.feature.home.view.HomeFragment
import br.com.efilhodev.marvel_heroes.feature.search.gateway.SearchViewModel
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    private lateinit var viewmodel: SearchViewModel

    private lateinit var adapter: SearchListAdapter
    private var isInitialLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = getQueryNameArg()

        viewmodel.getCharactersByName(query)
        search_error_text_view.setOnClickListener { viewmodel.getCharactersByName(query) }

        setupCharacterListAdapter()
    }

    private fun getQueryNameArg(): String? {
        return intent.getStringExtra(SearchActivity.QUERY_NAME_ARG)
    }

    override fun initViewModel(): BaseViewModel? {
        viewmodel = viewmodelFactory.create(SearchViewModel::class.java)
        return viewmodel
    }

    override fun onNetworkConnectionChangedStatus(isConnected: Boolean) {
        if (isConnected.not()) showErrorSnackBar(search_root, getString(R.string.error_connection))
        else hideErrorSnackBar()
    }

    private fun setupCharacterListAdapter() {
        viewmodel.getFavoritesCharactersIds()
        observeFavoritesCharacterIds()
    }

    private fun observeFavoritesCharacterIds() {
        viewmodel.actionFavoritesCharactersIds.observe(this, Observer {
            if (this::adapter.isInitialized.not()) startCharacterListAdapter(it)
            else adapter.updateFavorites(it)
        })
    }

    private fun startCharacterListAdapter(favoritesCharactersIds: List<Int>) {
        adapter = SearchListAdapter(
            onFavoriteClick = { character, isFavorite ->
                viewmodel.setOrRemoveFavoriteCharacter(character, isFavorite)
            },
            onDetailClick = { character ->
                navigateToCharacterDetailActivity(character)
            },
            favorites = favoritesCharactersIds
        )

        setupRecyclerView()

        observeGetCharacters()
        observeCharacterDataSourceState()
    }

    private fun setupRecyclerView() {
        search_recycler_view.layoutManager = StaggeredGridLayoutManager(
            HomeFragment.DEFAULT_SPAN_COUNT,
            RecyclerView.VERTICAL
        )
        search_recycler_view.adapter = adapter
    }

    private fun navigateToCharacterDetailActivity(character: Character) {
        val bundle = Bundle()
        bundle.putSerializable(CharacterDetailActivity.CHARACTER_ARG, character)

        Navigation.navigate(this, CharacterDetailActivity::class.java, bundle)
    }

    private fun observeGetCharacters() {
        viewmodel.getCharacters.observe(
            this,
            Observer { characters ->
                run {
                    if (characters.isEmpty()) search_empty_list_text_view.visibility = View.VISIBLE
                    else adapter.submitList(characters)
                }
            })
    }

    private fun observeCharacterDataSourceState() {
        viewmodel.state.observe(
            this,
            Observer { setViewState(it) }
        )
    }

    private fun setViewState(state: DataSourceState) {
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
        if (isInitialLoad) {
            search_progress_bar.visibility = View.GONE
            search_error_text_view.visibility = View.VISIBLE
        }
    }

    private fun showLoadingState() {
        if (isInitialLoad) {
            search_progress_bar.visibility = View.VISIBLE
            search_error_text_view.visibility = View.GONE
        }
    }

    private fun showDoneState() {
        isInitialLoad = false
        search_progress_bar.visibility = View.GONE
        search_error_text_view.visibility = View.GONE
    }

    companion object {
        const val QUERY_NAME_ARG = "ARG_QUERY_NAME"
    }
}