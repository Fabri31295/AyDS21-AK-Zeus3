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
                MoreDetailsUiEvent.ShowArtistInfo -> showArtistInfo()
                is MoreDetailsUiEvent.OpenWikipediaUrl -> openWikipediaUrl()
            }
        }

    private fun showArtistInfo() {
        Thread {
            moreDetailsModel.getArtistByName(moreDetailsView.uiState.artistName)
            moreDetailsView.updateUrl(moreDetailsView.uiState.urlString)
        }.start()
    }

    private fun openWikipediaUrl() {
        moreDetailsView.openWikipediaPage()
    }

}

