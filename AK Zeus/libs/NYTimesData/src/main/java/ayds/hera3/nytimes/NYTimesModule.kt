package ayds.hera3.nytimes

import ayds.hera3.nytimes.internal.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object NYTimesModule {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/svc/search/v2/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val nytAPI: NYTimesAPI = retrofit.create(NYTimesAPI::class.java)

    private val articleResolver: NYTToArticleResolver = JsonToArticleResolver()

    val nyTimesArticleService: NYTimesArticleService = NYTimesArticleServiceImpl(nytAPI, articleResolver)
}