package fr.orlandini.news.data.model

data class Source(
    val id: String?,
    val name: String?
) {

    override fun toString(): String {
        return "Source(id=$id, name=$name)"
    }
}