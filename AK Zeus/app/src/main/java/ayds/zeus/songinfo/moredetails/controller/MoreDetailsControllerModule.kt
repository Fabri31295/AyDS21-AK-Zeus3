package ayds.zeus.songinfo.moredetails.controller

import ayds.zeus.songinfo.moredetails.model.MoreDetailsModelModule
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView

object MoreDetailsControllerModule {

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        MoreDetailsControllerImpl(MoreDetailsModelModule.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }
    }
}