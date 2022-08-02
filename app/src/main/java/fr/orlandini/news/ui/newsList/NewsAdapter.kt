package fr.orlandini.news.ui.newsList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.orlandini.news.R
import fr.orlandini.news.data.model.Article
import fr.orlandini.news.databinding.ItemNewsBinding
import fr.orlandini.news.utils.Extensions.loadImage

/**
 * Permet de remplir la liste affichée à l'écran avec les données
 */
class NewsAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<NewsAdapter.TestViewHolder>() {
    private var articles = ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        // Initialisation du ViewHolder
        val binding: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        )

        return TestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        /*
        Récupération de l'élément de la liste de news correspondant à la position
        dans l'affichage
         */
        val news = articles[position]

        // Affichage des données automatique grâce au databinding
        holder.binding.item = news

        // Chargement de l'image de la news depuis l'URL [urlToImage]
        holder.binding.img.loadImage(news.urlToImage)

        // Action lors de la sélection d'un élément de la liste
        holder.itemView.setOnClickListener {
            // Passage de la news sélectionné en paramètre
            onClickListener.onClick(news)
        }
    }

    // Nombre d'éléments dans la liste
    override fun getItemCount(): Int = articles.size

    class TestViewHolder(binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: ItemNewsBinding

        init {
            this.binding = binding
        }
    }

    // Mise à jour des news sur l'interface
    fun setItems(newList: List<Article>?) {
        if (newList == null) {
            Log.w("NewsAdapter", "la liste est null, aucune mise à jour")
            return
        }
        /*
         Récupération des éléments modifiés entre l'ancienne liste et la nouvelle en
         paramètre de la methode
         */
        val diffCallback = NewsDiffCallback(articles, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // Mise à jour des données
        articles.clear()
        articles.addAll(newList)

        // Envoi des mises à jour a effectuer sur l'interface à l'adapter
        diffResult.dispatchUpdatesTo(this)
    }

    // Classe d'initialisation de clickListener
    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }
}