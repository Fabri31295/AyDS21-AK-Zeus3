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
                MoreDetailsUiEvent.ShowCardInfo -> showCardInfoAsync()
                is MoreDetailsUiEvent.OpenCardUrl -> openSourcePage()
            }
        }

    private fun showCardInfoAsync() {
        Thread {
            moreDetailsModel.searchCard(moreDetailsView.uiState.artistName)
        }.start()
    }

    private fun openSourcePage() {
        moreDetailsView.openSourcePage()
    }

}

