package ayds.zeus.songinfo.moredetails.model.repository.broker.proxies

import ayds.hera3.nytimes.NYTimesArticleService
import ayds.hera3.nytimes.entities.Article
import ayds.hera3.nytimes.entities.EmptyArticle
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source

internal class NYTimesProxy(private val service: NYTimesArticleService) : Proxy {
    override fun getCard(artistName: String): Card {
        return when (val artistInfo = service.getArticle(artistName)){
            is EmptyArticle -> EmptyCard()
            else -> map(artistInfo)
        }
    }

    private fun map(article: Article) = Card(
        article.artistInfo,
        article.url,
        article.logoUrl,
        Source.NYTIMES
    )
}