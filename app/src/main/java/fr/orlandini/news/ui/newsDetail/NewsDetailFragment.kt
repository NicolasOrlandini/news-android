package fr.orlandini.news.ui.newsDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import fr.orlandini.news.data.repository.NewsRepository
import fr.orlandini.news.databinding.FragmentNewsDetailBinding
import fr.orlandini.news.ui.viewModel.NewsViewModel
import fr.orlandini.news.ui.viewModel.NewsViewModelFactory
import fr.orlandini.news.utils.Extensions.loadImage

/**
 * Page permettant d'afficher le détail d'une news
 */
class NewsDetailFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels {
        NewsViewModelFactory(NewsRepository())
    }

    private var _binding: FragmentNewsDetailBinding? = null

    // Getter de binding, valabale jusqu'à la destruction de la vue
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Intitialisation de la vue et du databinding
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Chargement de l'image avec l'URL
        binding.img.loadImage(binding.news?.urlToImage)


        // Retour à la liste
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Action lors du clic sur le bouton "Voir l'article complet"
        binding.btnToWeb.setOnClickListener {
            try {
                // Ouverture du navigateur par défaut avec l'URL de la news
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(binding.news?.url)
                startActivity(openURL)
            } catch (e: Exception) {
                Log.e(TAG, "Impossible d'ouvrir la page web : ${e.message}")
            }
        }

        /*
        Lorsqu'un une news est sélectionné dans la liste,
        les données affichées à l'écran sont mises à jour
         */
        viewModel.selectedArticle.observe(viewLifecycleOwner) { news ->
            Log.d(TAG, "observe : $news")
            binding.news = news
            binding.img.loadImage(news.urlToImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Suppression du binding lors de la destruction de la vue
        _binding = null
    }

    companion object {
        private const val TAG = "NewsDetailFragment"

        fun newInstance() = NewsDetailFragment()
    }
}