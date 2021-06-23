package ayds.zeus.songinfo.moredetails.view

import ayds.zeus.songinfo.moredetails.controller.MoreDetailsControllerModule
import ayds.zeus.songinfo.moredetails.model.MoreDetailsModelModule

object MoreDetailsViewModule {

    val cardInfoHelper: CardDescriptionHelper = CardDescriptionHelperImpl()
    
    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelModule.onViewStarted(moreDetailsView)
        MoreDetailsControllerModule.onViewStarted(moreDetailsView)
    }
}