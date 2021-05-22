package ayds.zeus.songinfo.moredetails.fulllogic.controller

import ayds.zeus.songinfo.moredetails.fulllogic.model.MoreDetailsModel
import ayds.zeus.songinfo.moredetails.fulllogic.view.MoreDetailsView

interface MoreDetailsController{
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

internal class MoreDetailsControllerImpl(private val moreDetailsModel: MoreDetailsModel) : MoreDetailsController {

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
    }

    private fun viewFullArticle(){

    }

    private fun openWikipediaURL(){
        
    }

}

