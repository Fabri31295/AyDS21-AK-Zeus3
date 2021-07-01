package ayds.hera3.nytimes

import ayds.hera3.nytimes.entities.Article

interface NYTimesArticleService {

    fun getArticle(artistName: String): Article
}