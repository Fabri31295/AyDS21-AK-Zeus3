package ayds.zeus.songinfo.moredetails.model

import android.content.Context
import ayds.apolo2.LastFM.LastFMAPIArtistModule
import ayds.hera3.nytimes.NYTimesModule
import ayds.zeus.songinfo.moredetails.model.entities.Source
import ayds.zeus.songinfo.moredetails.model.repository.broker.Broker
import ayds.zeus.songinfo.moredetails.model.repository.broker.BrokerImpl
import ayds.zeus.songinfo.moredetails.model.repository.broker.proxies.LastFMProxy
import ayds.zeus.songinfo.moredetails.model.repository.broker.proxies.WikipediaProxy
import ayds.zeus.songinfo.moredetails.model.repository.CardRepository
import ayds.zeus.songinfo.moredetails.model.repository.CardRepositoryImpl
import ayds.zeus.songinfo.moredetails.model.repository.broker.proxies.NYTimesProxy
import ayds.zeus.songinfo.moredetails.model.repository.local.CursorToCardMapperImpl
import ayds.zeus.songinfo.moredetails.model.repository.local.CardLocalStorage
import ayds.zeus.songinfo.moredetails.model.repository.local.CardLocalStorageImpl
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView
import ayds.zeus3.wikipedia.WikipediaModule

object MoreDetailsModelModule {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        val cardLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            moreDetailsView as Context,
            CursorToCardMapperImpl()
        )
        val proxyMap = mapOf(
            Pair(Source.WIKIPEDIA, WikipediaProxy(WikipediaModule.wikipediaService)),
            Pair(Source.LASTFM, LastFMProxy(LastFMAPIArtistModule.lastFMAPIArtistService)),
            Pair(Source.NYTIMES, NYTimesProxy(NYTimesModule.nyTimesArticleService))
        )
        val broker: Broker = BrokerImpl(proxyMap)
        val cardRepository: CardRepository =
            CardRepositoryImpl(cardLocalStorage, broker)
        moreDetailsModel = MoreDetailsModelImpl(cardRepository)
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}