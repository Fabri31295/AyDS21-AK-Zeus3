package ayds.zeus.songinfo.moredetails.model

import android.content.Context
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CardLocalStorage
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CardLocalStorageImpl
import ayds.zeus.songinfo.moredetails.model.repository.CardRepository
import ayds.zeus.songinfo.moredetails.model.repository.CardRepositoryImpl
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaModule
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CursorToCardMapperImpl
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView

object MoreDetailsModelModule {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        val cardLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            moreDetailsView as Context,
            CursorToCardMapperImpl()
        )
        val wikipediaService: WikipediaService = WikipediaModule.wikipediaService
        val cardRepository: CardRepository =
            CardRepositoryImpl(cardLocalStorage, wikipediaService)
        moreDetailsModel = MoreDetailsModelImpl(cardRepository)
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}