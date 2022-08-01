package fr.orlandini.news.data.model

class NewsResponse {
    val status: String? = null
    val totalResults: Int? = null
    val articles: List<Article>? = null

    override fun toString(): String {
        return "NewsResponse(status=$status, totalResults=$totalResults, newsList=$articles)"
    }
}