package ayds.zeus.songinfo.moredetails.model

import android.content.Context
import ayds.zeus.songinfo.moredetails.model.broker.Broker
import ayds.zeus.songinfo.moredetails.model.broker.BrokerImpl
import ayds.zeus.songinfo.moredetails.model.broker.proxies.Proxy
import ayds.zeus.songinfo.moredetails.model.broker.proxies.WikipediaProxy
import ayds.zeus.songinfo.moredetails.model.repository.CardRepository
import ayds.zeus.songinfo.moredetails.model.repository.CardRepositoryImpl
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
        val wikipediaProxy: Proxy = WikipediaProxy(WikipediaModule.wikipediaService)
        val broker: Broker = BrokerImpl(wikipediaProxy)
        val cardRepository: CardRepository =
            CardRepositoryImpl(cardLocalStorage, broker)
        moreDetailsModel = MoreDetailsModelImpl(cardRepository)
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}