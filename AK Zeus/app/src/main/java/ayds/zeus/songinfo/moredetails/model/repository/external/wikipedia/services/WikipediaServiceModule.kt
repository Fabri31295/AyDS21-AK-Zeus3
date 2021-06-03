package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.services

import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object WikipediaServiceModule {
    private const val URL_WIKIPEDIA = "https://en.wikipedia.org/w/"
    private val wikipediaAPIretrofit = Retrofit.Builder()
        .baseUrl(URL_WIKIPEDIA)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val wikipediaAPI = wikipediaAPIretrofit.create(WikipediaAPI::class.java)
    private val wikipediaToArticleResolver: WikipediaToArticleResolver = JsonToArticleResolver()
    val wikipediaService: WikipediaService = WikipediaServiceImpl(
        wikipediaToArticleResolver,
        wikipediaAPI
    )
}