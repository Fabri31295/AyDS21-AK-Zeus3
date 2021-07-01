package ayds.zeus.songinfo.moredetails.model.repository.broker.proxies

import ayds.apolo2.LastFM.LastFMAPIArtistService
import ayds.apolo2.LastFM.entities.InfoArtist
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.entities.EmptyCard
import ayds.zeus.songinfo.moredetails.model.entities.Source

internal class LastFMProxy(private val service: LastFMAPIArtistService) : Proxy {

    override fun getCard(artistName: String): Card {
        val artistInfo = service.getArtist(artistName)
        return if (artistInfo == null)
            EmptyCard()
        else
            map(artistInfo)
    }

    private fun map(article: InfoArtist) = Card(
        article.description,
        article.infoUrl,
        article.sourceLogoUrl,
        Source.LASTFM
    )
}