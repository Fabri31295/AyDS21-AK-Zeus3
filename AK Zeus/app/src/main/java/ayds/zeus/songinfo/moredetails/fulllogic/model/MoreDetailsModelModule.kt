package ayds.zeus.songinfo.moredetails.fulllogic.model

import android.content.Context
import ayds.zeus.songinfo.moredetails.fulllogic.view.MoreDetailsView

object MoreDetailsModelModule {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun onViewStarted(moreDetailsView: MoreDetailsView){
        val artistInfoStorage : ArtistInfoStorage = ArtistInfoStorageImpl(moreDetailsView as Context)
        moreDetailsModel = MoreDetailsModelImpl(artistInfoStorage);
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}