package fr.orlandini.news.data.network

import fr.orlandini.news.BuildConfig
import fr.orlandini.news.data.model.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Configuration des requêtes http avec Retrofit
 */
interface NewsService {

    /*
    Configuration requête de récupération des derniers gros titres français,
    Paramètres : langue des news, ordre de tri par date
    La clé d'API est ajoutée dans le header pour plus de sécurité
     */
    @Headers("Authorization: ${BuildConfig.NEWS_KEY}")
    @GET("v2/top-headlines")
    fun geTopNews(@Query("country") language: String): Call<NewsResponse>

    companion object {
        // Instance statique de retrofit
        private var newsService: NewsService? = null

        /*
        Récupération de l'instance statique de retrofit.
        Déserialisation avec Gson
         */
        fun getInstance(): NewsService {
            if (newsService == null) {

                // Log des requêtes et réponses
                val logging = HttpLoggingInterceptor()

                // Niveau de log
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
                httpClient.addInterceptor(logging)

                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                newsService = retrofit.create(NewsService::class.java)
            }
            return newsService!!
        }
    }
}
