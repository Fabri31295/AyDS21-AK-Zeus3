package ayds.zeus.songinfo.moredetails.controller

import ayds.zeus.songinfo.moredetails.model.MoreDetailsModel
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView
import ayds.zeus.songinfo.moredetails.view.MoreDetailsUiEvent
import ayds.observer.Observer

interface MoreDetailsController {
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

internal class MoreDetailsControllerImpl(private val moreDetailsModel: MoreDetailsModel) :
    MoreDetailsController {

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when (value) {
                MoreDetailsUiEvent.ShowArtistInfo -> showArtistInfoAsync()
                is MoreDetailsUiEvent.OpenWikipediaUrl -> openWikipediaUrl()
            }
        }

    private fun showArtistInfoAsync() {
        Thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        val wikipediaArticle = moreDetailsModel.getArticle(moreDetailsView.uiState.artistName)
        moreDetailsView.updateUrl(wikipediaArticle.url)
        moreDetailsView.showArtistInfoActivity(wikipediaArticle.info, wikipediaArticle.isLocallyStoraged)
    }

    private fun openWikipediaUrl() {
        moreDetailsView.openWikipediaPage()
    }

}

