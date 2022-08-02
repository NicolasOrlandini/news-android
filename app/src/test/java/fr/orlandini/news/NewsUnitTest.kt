package fr.orlandini.news

import com.google.gson.Gson
import fr.orlandini.news.data.model.Article
import org.junit.Test

import org.junit.Assert.*

/**
 * Tests unitaires divers
 */
class NewsUnitTest {
    @Test
    fun testGetFormattedDate() {

        val gson = Gson()

        // Cas publishedAt = null
        var article = gson.fromJson("{}", Article::class.java)
        assertEquals("", article.getFormattedDate())

        // Cas publishedAt au bon format d'entrée
        article = gson.fromJson("{\"publishedAt\":\"2022-08-01T19:39:38Z\"}", Article::class.java)
        // Test si l'objet a bien été initialisé
        assertEquals("2022-08-01T19:39:38Z", article.publishedAt)
        // Test si le formattage est bien effectué
        assertEquals("01 août 2022 19:39", article.getFormattedDate())

        // Cas publishedAt = ""
        article = gson.fromJson("{\"publishedAt\":\"\"}", Article::class.java)
        assertEquals("", article.getFormattedDate())

        // Cas publishedAt au mauvais format
        article = gson.fromJson("{\"publishedAt\":\"2022-08-01\"}", Article::class.java)
        assertEquals("", article.getFormattedDate())
    }

    @Test
    fun testArticleFromJson() {
        val jsonString =
            "{\"source\":{\"id\":null,\"name\":\"Francetvinfo.fr\"},\"author\":null,\"title\":\"" +
                    "Sécheresse: Paris et la petite couronne sous \\\"vigilance\\\", " +
                    "toute la France hexagonale sous surveillance - franceinfo\"," +
                    "\"description\":\"La préfecture d'Ile-de-France va placer ce mardi en " +
                    "vigilance sécheresse Paris, les Hauts-de-Seine, la Seine-Saint-Denis et " +
                    "le Val-de-Marne.\",\"url\":\"https://www.francetvinfo.fr/meteo/secheresse/" +
                    "secheresse-paris-et-la-petite-couronne-places-en-vigilance-toute-la-france-" +
                    "metropolitaine-desormais-sous-surveillance_5289088.html\"," +
                    "\"urlToImage\":\"https://www.francetvinfo.fr/pictures/oFUD2tYA8pTqk_B7_aU6kk-" +
                    "w6Kk/1500x843/2022/08/02/phpJZvH2u.jpg\"," +
                    "\"publishedAt\":\"2022-08-02T05:48:38Z\"," +
                    "\"content\":\"La préfecture d'Ile-de-France va placer ce mardi en vigilance " +
                    "sécheresse Paris, les Hauts-de-Seine, la Seine-Saint-Denis et le Val-de-" +
                    "Marne. Paris, les Hauts-de-Seine, la Seine-Saint-Denis et le Val-… " +
                    "[+844 chars]\"}"

        val gson = Gson()
        val article = gson.fromJson(jsonString, Article::class.java)


        assertEquals(
            "La préfecture d'Ile-de-France va placer ce mardi en " +
                    "vigilance sécheresse Paris, les Hauts-de-Seine, la Seine-Saint-Denis et " +
                    "le Val-de-Marne.", article.description
        )

        assertEquals("2022-08-02T05:48:38Z", article.publishedAt)
    }
}