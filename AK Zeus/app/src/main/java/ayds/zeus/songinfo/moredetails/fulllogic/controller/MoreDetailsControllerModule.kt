package ayds.zeus.songinfo.moredetails.fulllogic.controller

import ayds.zeus.songinfo.moredetails.fulllogic.model.MoreDetailsModelModule
import ayds.zeus.songinfo.moredetails.fulllogic.view.MoreDetailsView

object MoreDetailsControllerModule {

    fun onViewStarted(moreDetailsView: MoreDetailsView){
        MoreDetailsControllerImpl(MoreDetailsModelModule.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }
    }
}