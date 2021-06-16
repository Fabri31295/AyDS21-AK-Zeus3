package ayds.zeus.songinfo.moredetails.model

import android.content.Context
import ayds.zeus.songinfo.moredetails.model.repository.CardRepository
import ayds.zeus.songinfo.moredetails.model.repository.CardRepositoryImpl
import ayds.zeus.songinfo.moredetails.model.repository.local.ArticleToCardMapperImpl
import ayds.zeus.songinfo.moredetails.model.repository.local.CursorToCardMapperImpl
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CardLocalStorage
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CardLocalStorageImpl
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView
import ayds.zeus3.wikipedia.WikipediaModule.wikipediaService

object MoreDetailsModelModule {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        val cardLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            moreDetailsView as Context,
            CursorToCardMapperImpl()
        )
        val articleToCardMapper = ArticleToCardMapperImpl()
        val cardRepository: CardRepository =
            CardRepositoryImpl(cardLocalStorage, wikipediaService, articleToCardMapper)
        moreDetailsModel = MoreDetailsModelImpl(cardRepository)
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}