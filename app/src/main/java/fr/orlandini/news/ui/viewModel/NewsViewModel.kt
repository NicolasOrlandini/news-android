package fr.orlandini.news.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.orlandini.news.data.model.Article
import fr.orlandini.news.data.model.NewsResponse
import fr.orlandini.news.data.repository.NewsRepository
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

/**
 * Permet de stocker et gérer les données utilisées dans l'interface
 */
class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    /*
    Liste des news, cette liste peut être écoutée pour mettre à jour l'interface
    lors d'une modification
    */
    val articles = MutableLiveData<List<Article>>()

    // Dernière news sélectionnée dans la liste
    val selectedArticle = MutableLiveData<Article>()

    // Booléen permettant de déterminer si une requête est en cours ou non
    val loading = MutableLiveData<Boolean>()

    // Permet de récupérer les news et mettre à jour les données
    // Le paramètre auto permet de ne pas afficher la page de chargement (cas du pull to refresh)
    fun getTopNews(auto: Boolean = false) {
        if (!auto) loading.value = true
        val request = repository.getTopNews()

        // Exécution de la requête de manière asynchrone
        request.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d(
                    "NewsListViewModel",
                    "Réponse requête news : ${response.body().toString()}"
                )

                loading.value = false
                // Mise à jour des news avec le contenu de la réponse de la requête
                articles.value = response.body()?.articles ?: emptyList()
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(
                    "NewsListViewModel",
                    "Erreur lors de la récupération des news : ${t.message}"
                )

                loading.value = false
                // Une liste vide affectée en cas d'echec
                articles.value = emptyList()
            }
        })
    }

    // Mise à jour de la news sélectionnée
    fun selectNews(article: Article) {
        selectedArticle.value = article
    }
}