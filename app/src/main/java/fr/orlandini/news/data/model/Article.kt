package fr.orlandini.news.data.model

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
) {
    /*
    Permet de formatter la date pour l'afficher de manière lisible à l'écran
    Cette methode peut directement être utilisé pour le databinding (item_news)
     */
    fun getFormattedDate(): String {
        if (publishedAt == null) return ""

        try {
            // Format d'entrée correspond au format de [publishedAt]
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.FRANCE)

            // Format de sortie, lisible pour l'affichage, au format français
            val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.FRANCE)

            // On converti [publishedAt] en date et on retourne un string vide en cas d'echec
            val date: Date = inputFormat.parse(publishedAt) ?: return ""

            return outputFormat.format(date)
        } catch (e: ParseException) {
            Log.e("News", "Impossible de convertir la date : ${e.message}")
            return ""
        }
    }
}