package ayds.zeus.songinfo.moredetails.fulllogic.view

import ayds.zeus.songinfo.moredetails.fulllogic.controller.MoreDetailsControllerModule
import ayds.zeus.songinfo.moredetails.fulllogic.model.MoreDetailsModelModule

object MoreDetailsViewModule {
    fun init(moreDetailsView: MoreDetailsView){
        MoreDetailsControllerModule.onViewStarted(moreDetailsView)
        MoreDetailsModelModule.onViewStarted(moreDetailsView)
    }
}