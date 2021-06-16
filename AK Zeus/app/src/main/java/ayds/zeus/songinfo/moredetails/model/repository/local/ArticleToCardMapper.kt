package ayds.zeus.songinfo.moredetails.model.repository.local

import ayds.zeus.songinfo.moredetails.model.repository.Source
import ayds.zeus.songinfo.moredetails.model.repository.entities.Card
import ayds.zeus3.wikipedia.Article

interface ArticleToCardMapper {
    fun map(article: Article): Card
}

internal class ArticleToCardMapperImpl : ArticleToCardMapper {
    override fun map(article: Article) = Card(
        article.info,
        article.url,
        article.logoUrl,
        Source.WIKIPEDIA,
        false
    )

}