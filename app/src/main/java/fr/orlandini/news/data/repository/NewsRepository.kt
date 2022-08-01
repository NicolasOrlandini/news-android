package fr.orlandini.news.data.repository

import fr.orlandini.news.data.network.NewsService

/**
 Permet de récupérer les données sur différentes sources et de les rendre accessible
 au reste de l'application
 */
class NewsRepository {

    // Récupération de la liste des news en français via l'API Google News
    fun getTopNews() = NewsService.getInstance().geTopNews("fr")
}