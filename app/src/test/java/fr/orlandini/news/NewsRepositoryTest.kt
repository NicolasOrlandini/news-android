package fr.orlandini.news

import fr.orlandini.news.data.repository.NewsRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class NewsRepositoryTest {
    lateinit var mainRepository: NewsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = NewsRepository()
    }

    @Test
    fun getTopNewstest() {
        val response = mainRepository.getTopNews().execute()
        assertEquals(200, response.code())
    }
}