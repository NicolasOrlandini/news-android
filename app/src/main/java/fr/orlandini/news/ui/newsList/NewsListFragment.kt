package fr.orlandini.news.ui.newsList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import fr.orlandini.news.R
import fr.orlandini.news.data.repository.NewsRepository
import fr.orlandini.news.databinding.FragmentNewsListBinding
import fr.orlandini.news.ui.newsDetail.NewsDetailFragment
import fr.orlandini.news.ui.viewModel.NewsViewModel
import fr.orlandini.news.ui.viewModel.NewsViewModelFactory

/**
 * Page contenant la liste de toutes les news
 */
class NewsListFragment : Fragment(), LifecycleObserver {

    private var _binding: FragmentNewsListBinding? = null

    // Getter de binding, valabale jusqu'à la destruction de la vue
    private val binding get() = _binding!!

    // Initialisation du ViewModel pour lier l'affichage aux données
    private val viewModel: NewsViewModel by activityViewModels {
        NewsViewModelFactory(NewsRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Intitialisation de la vue et du databinding
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        // Définition du lifecycleOwner pour observer les modifications des données
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // On applique le viewModel au binding
        binding.apply {
            newsViewModel = viewModel
        }

        val newsAdapter = NewsAdapter(NewsAdapter.OnClickListener { news ->
            Log.d(TAG, "onClick item NewsAdapter : $news")
            /*
            Sélection de la news dans le viewModel, le fragment newsDetail est
            mis à jour automatiquement
             */
            viewModel.selectNews(news)

            // Affichage du fragment News Detail
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.fragment_news, NewsDetailFragment.newInstance())
            // Ajout d'une animation à l'ouverture de la vue
            ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // Ajout en backstack
            ft?.addToBackStack(null)
            ft?.commit()
        })

        /*
        On défini le layoutManager. Ici il s'agit d'afficher les news en liste simple,
        donc on utilise un LinearLayoutManager
         */
        binding.recyclerNews.layoutManager = LinearLayoutManager(context)
        binding.recyclerNews.adapter = newsAdapter

        binding.swipeRefreshNews.setOnRefreshListener { viewModel.getTopNews(true) }

        /*
        Lorsqu'un un changement est détecté sur la liste des news,
        les données affichées à l'écran sont mises à jour
         */
        viewModel.articles.observe(viewLifecycleOwner) { news ->
            Log.d(TAG, "observe : $news")
            binding.swipeRefreshNews.isRefreshing = false
            newsAdapter.setItems(news)
        }

        // Récupération des news une première fois au démarrage
        viewModel.getTopNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Suppression du binding lors de la destruction de la vue
        _binding = null
    }

    companion object {
        private const val TAG = "NewsListFragment"

        // Nouvelle instance du fragment
        fun newInstance() = NewsListFragment()
    }
}