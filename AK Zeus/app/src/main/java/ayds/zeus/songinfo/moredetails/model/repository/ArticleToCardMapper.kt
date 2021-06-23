package ayds.zeus.songinfo.moredetails.model.repository

import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.Source
import ayds.zeus3.wikipedia.WikipediaArticle

interface ArticleToCardMapper {
    fun map(article: WikipediaArticle, source: Source): Card
}

internal class ArticleToCardMapperImpl : ArticleToCardMapper {
    override fun map(article: WikipediaArticle, source: Source) = Card(
        article.info,
        article.url,
        article.logoUrl,
        source
    )

}