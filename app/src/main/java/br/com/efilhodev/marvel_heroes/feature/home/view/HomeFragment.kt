package br.com.efilhodev.marvel_heroes.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.efilhodev.marvel_heroes.R
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.base.view.BaseFragment
import br.com.efilhodev.marvel_heroes.feature.base.view.Navigation
import br.com.efilhodev.marvel_heroes.feature.details.view.CharacterDetailActivity
import br.com.efilhodev.marvel_heroes.feature.home.gateway.DataSourceState
import br.com.efilhodev.marvel_heroes.feature.home.gateway.HomeViewModel
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private lateinit var viewmodel: HomeViewModel
    private lateinit var adapter: CharacterListAdapter

    private var isInitialLoad = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initViewModel(): BaseViewModel {
        viewmodel = viewmodelFactory.create(HomeViewModel::class.java)
        return viewmodel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCharacterListAdapter()
        setupCharacterListRetry()
    }

    private fun setupCharacterListAdapter() {
        viewmodel.getFavoritesCharactersIds()
        observeFavoritesCharacterIds()
    }

    private fun observeFavoritesCharacterIds() {
        viewmodel.actionFavoritesCharactersIds.observe(viewLifecycleOwner, Observer {
            if (this::adapter.isInitialized.not()) startCharacterListAdapter(it)
            else adapter.updateFavorites(it)
        })
    }

    private fun startCharacterListAdapter(favoritesCharactersIds: List<Int>) {
        adapter = CharacterListAdapter(
            onRetryClick = { viewmodel.retryCharactersLoad() },

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
        home_recycler_view.layoutManager = StaggeredGridLayoutManager(
            DEFAULT_SPAN_COUNT,
            RecyclerView.VERTICAL
        )
        home_recycler_view.adapter = adapter
    }

    private fun navigateToCharacterDetailActivity(character: Character) {
        val bundle = Bundle()
        bundle.putSerializable(CharacterDetailActivity.CHARACTER_ARG, character)

        Navigation.navigate(context, CharacterDetailActivity::class.java, bundle)
    }

    private fun observeGetCharacters() {
        viewmodel.actionGetCharacters.observe(
            viewLifecycleOwner,
            Observer { characters -> adapter.submitList(characters) })
    }

    private fun observeCharacterDataSourceState() {
        viewmodel.actionCharacterDataSourceState.observe(
            viewLifecycleOwner,
            Observer { setViewState(it) }
        )
    }

    private fun setViewState(state: DataSourceState) {
        adapter.setState(state)

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
            home_progress_bar.visibility = View.GONE
            home_error_text_view.visibility = View.VISIBLE
        }
    }

    private fun showLoadingState() {
        if (isInitialLoad) {
            home_progress_bar.visibility = View.VISIBLE
            home_error_text_view.visibility = View.GONE
        }
    }

    private fun showDoneState() {
        isInitialLoad = false
        home_progress_bar.visibility = View.GONE
        home_error_text_view.visibility = View.GONE
    }

    private fun setupCharacterListRetry() {
        home_error_text_view.setOnClickListener { viewmodel.retryCharactersLoad() }
    }

    companion object {
        private const val DEFAULT_SPAN_COUNT = 2
    }
}