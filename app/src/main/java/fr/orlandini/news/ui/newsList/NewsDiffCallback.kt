package fr.orlandini.news.ui.newsList

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import fr.orlandini.news.data.model.Article

/**
 * Cette classe permet de vérifier si les éléments à la même position dans de listes sont identiques
 * ou non
 * Utilisé dans l'adapteur, permet de recharger seulement les éléments modifiés sur l'affichage
 */
class NewsDiffCallback(private val mOldList: List<Article>,
                       private val mNewList: List<Article>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        val size = getListSize(mOldList)
        Log.d(TAG, "Old List Size : $size")
        return size
    }

    override fun getNewListSize(): Int {
        val size = getListSize(mNewList)
        Log.d(TAG, "New List Size : $size")
        return size
    }

    // Retourne la taille de la liste
    private fun getListSize(list: List<Article>) = list.size

    // Vérifie si l'item est bien à la même position
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = mNewList[newItemPosition]
        val oldItem = mOldList[oldItemPosition]
        val same = (oldItem.url == newItem.url)
        Log.d(TAG, "areItemsTheSame : $same - oldItem url : ${oldItem.url} vs newItem url : ${newItem.url}")
        return same
    }

    // Vérifie si le contenue de l'item est identique
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = mNewList[newItemPosition]
        val oldItem = mOldList[oldItemPosition]
        val same = (oldItem.author == newItem.author
                && oldItem.title == newItem.title
                && oldItem.description == newItem.description
                && oldItem.url == newItem.url
                && oldItem.urlToImage == newItem.urlToImage
                && oldItem.publishedAt == newItem.publishedAt
                && oldItem.content == newItem.content)
        Log.d(TAG, "areContentsTheSame : same = $same")
        return same
    }

    companion object {
        private const val TAG = "NewsDiffCallback"
    }
}