# News Android   
   
Cette application permet d'accéder aux dernières news en français.   

Language Kotlin   
Architecture MVVM (Model View ViewModel)   

## Architecture

L'Architecture est la suivante :

Le model : Il s'agit ici de data class (Article et Source), la data class implémente par défaut les méthodes `equals()` ainsi que `toString()`, pratique pour le cas présent qui ne nécessite pas de traitement spécial.   
   
Le repository accède aux données pour les rendre accéssible au reste de l'application, ici il s'agit de l'API News.   
   
Le ViewModel fait le lien entre les données et les vues, il contient les différentes données à jour.   
Ici, le même ViewModel est partagé entre les 2 fragments puisqu'ils partagent les mêmes données. En particulier l'objet selectedItem qui permet de déterminer la news a afficher dans la page de détail. Il est créé une seule fois au premier besoin puis le même est réutilisé à chaque besoin dans les 2 fragments avec `by activityViewModels`.   
   
Dès qu'une donnée est modifiée dans le ViewModel (quelle que soit son origine), la modification est transmise aux vues grâce aux observers   
   
Pour optimiser le code et le rendre plus lisible, j'utilise du databinding.   
Les données à afficher dans la vue sont spécifiées directement dans le XML et donc mises à jour automatiquement. On a également accès aux composants de la vue dans le code pour faire des actions dans le code (chargement d'image, click).   
L'objet Article est utilisé pour afficher les données à l'écran sur la page de détail.   
Le ViewModel est directement utilisé dans le fragment qui contient la liste pour afficher ou non la page de chargement.   
   
## choix techniques   
   
Pour la structure des pages, j'ai choisi d'utiliser des fragments pour la liste des news ainsi que la page de détail. En effet, on peut imaginer par exemple que sur tablette la liste soit à gauche et le détail à droite.   
   
Pour coller au mieux avec l'énoncé, j'ai choisi d'utiliser l'endpoint `top-headlines` qui permet de récupérer les derniers news d'un pays (ici la France)   
   
Pour optimiser les temps de chargement de l'interface, j'utilise l'utilitaire DiffUtils.   
Cet utilitaire teste si chaque nouvel élément est identique à l'ancien ou non et informe l'adapteur des éléments à modifier qui met à jour seulement les éléments nécessaires l'interface.   
   
J'ai ajouté un pull to refresh pour pouvoir récupérer les dernières news facilement.   
   
Dans la classe Extensions se trouve une extension de AppCompatImageView loadImage qui permet de factoriser le code en n'implémentant qu'une seule fois la librairie Picasso   
   
Pour le design j'utilise un thème Material3 qui a l'avantage de s'adapter automatiquement au thème du téléphone (mode clair/mode sombre) sans code supplémentaire.   
   
### Librairies   
   
Concernant l'API News, une librairie non officielle est proposée dans la documentation, j'ai choisi de ne pas l'utiliser car elle n'apporte pas grand chose en terme d'optimisation et pour rendre l'exercice plus pertinent.   
J'ai donc choisi d'utiliser la librairie Retrofit pour effectuer la requête ainsi que Gson pour déserialiser la réponse de l'API. Gson permet de convertir un fichier json en objet kotlin.   
Nécessite d'ajouter la permission `<uses-permission android:name="android.permission.INTERNET"/>` dans le Manifest pour autoriser l'application à se servir de la connexion internet.   
   
J'ai choisi d'utiliser la librairie Picasso pour charger les images à partir des URL. il existe une librairie équivalente nommée Glide. L'avantage de ces librairies est qu'elles ont un système de gestion de cache, ce qui permet d'optimiser la consommation réseau en ne téléchargeant les images qu'une seule fois.   
   
La librairie Logging Interceptor de OkHttp permet d'afficher le contenu des requêtes ainsi que le résultat, bien pratique pour s'assurer que le code est correct.   
   
## Sécurité   
   
Pour plus de sécurité, je passe la clé d'API dans le header de la requête et je la stocke dans un fichier séparé .properties. Normalement, ce fichier doit être ignoré lors des commits dans le .gitignore mais ici je l'ai mis pour que l'application puisse fonctionner sans modification.   
   
## Tests   
   
J'ai réalisé quelques tests basiques (formatage de la date de publication de l'article, conversion depuis Json vers un objet Article ainsi que des tests de la requête sur l'API).
   
## idées d'évolutions   
   
- Pour maintenir à jour la liste des news, on peut ajouter une mise à jour régulière depuis l'API.   
- On remarque que la requête de récupération de tous les articles est paginée, on a donc par défaut les 100 dernières news publiées (maximum). On peut donc améliorer la réactivité et économiser le réseau en mettant en place un système de pagination.   
  Pour cela, il faut limiter le nombre de résultats avec le paramètre `pageSize` et charger les éléments à la demande (récupérer les éléments au fur et à mesure que l'utilisateur scroll dans la liste).   
  On peut utiliser la librairie Paging3 qui fait partie d'Android Jetpack par exemple.   
   
## Temps total   
   
Temps total passé sur le projet : **10h**