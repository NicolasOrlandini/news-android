package fr.orlandini.news.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.orlandini.news.data.repository.NewsRepository

/**
 * Permet d'instancier un ViewModel avec des arguments (ici c'est le repository)
 */
class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NewsViewModel(this.repository) as T
}