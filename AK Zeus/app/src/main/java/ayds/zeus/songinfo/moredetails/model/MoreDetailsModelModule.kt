package ayds.zeus.songinfo.moredetails.model

import android.content.Context
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.WikipediaLocalStorage
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.WikipediaLocalStorageImpl
import ayds.zeus.songinfo.moredetails.model.repository.ArticleRepository
import ayds.zeus.songinfo.moredetails.model.repository.ArticleRepositoryImpl
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaModule
import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.WikipediaService
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.CursorToWikipediaArticleMapperImpl
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView

object MoreDetailsModelModule {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        val wikipediaLocalStorage: WikipediaLocalStorage = WikipediaLocalStorageImpl(
            moreDetailsView as Context,
            CursorToWikipediaArticleMapperImpl()
        )
        val wikipediaService: WikipediaService = WikipediaModule.wikipediaService
        val articleRepository: ArticleRepository =
            ArticleRepositoryImpl(wikipediaLocalStorage, wikipediaService)
        moreDetailsModel = MoreDetailsModelImpl(articleRepository)
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}