package fr.orlandini.news

import com.google.gson.Gson
import fr.orlandini.news.data.model.NewsResponse
import fr.orlandini.news.data.network.NewsService
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Call

class NewsServiceTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
    }

    @Test
    fun getTopNewsApiTest() {

        val language = "fr"

        val call: Call<NewsResponse> = NewsService.getInstance().geTopNews(language)

        // Test si la requête est bien de type GET
        assertEquals("GET", call.request().method)
        // Test si le paramètre "langage"
        assertEquals(language, call.request().url.queryParameterValue(0))

        // Exécution de la requête
        val response = call.execute()

        // Vérification du error body
        val errorBody = response.errorBody()
        assert(errorBody == null)
        // Vérification du success body
        val responseWrapper = response.body()
        assert(responseWrapper != null)
        assert(response.code() == 200)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}