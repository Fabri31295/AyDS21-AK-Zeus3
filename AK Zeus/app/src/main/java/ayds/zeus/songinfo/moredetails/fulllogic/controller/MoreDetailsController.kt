package ayds.zeus.songinfo.moredetails.fulllogic.controller

import ayds.zeus.songinfo.moredetails.fulllogic.view.MoreDetailsView

interface FullLogicController {
    fun setFullLogic(moreDetailsView: MoreDetailsView)
}

internal class FullLogicControllerIMP (

) : MoreDetailsController {
    private lateinit var fullLogicView: FullLogicView

    override fun setFullLogic(fullLogicView: FullLogicView){
        this.fullLogicView= fullLogicView
    }

}

