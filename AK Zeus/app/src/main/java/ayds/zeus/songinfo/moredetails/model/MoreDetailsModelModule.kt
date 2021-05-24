package ayds.zeus.songinfo.moredetails.model

import android.content.Context
import ayds.zeus.songinfo.moredetails.model.repository.ArtistInfoStorage
import ayds.zeus.songinfo.moredetails.model.repository.ArtistInfoStorageImpl
import ayds.zeus.songinfo.moredetails.model.repository.ArtistRepository
import ayds.zeus.songinfo.moredetails.model.repository.ArtistRepositoryImpl
import ayds.zeus.songinfo.moredetails.view.MoreDetailsView

object MoreDetailsModelModule {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun onViewStarted(moreDetailsView: MoreDetailsView){
        val artistInfoStorage: ArtistInfoStorage = ArtistInfoStorageImpl(moreDetailsView as Context)
        val artistRepository: ArtistRepository = ArtistRepositoryImpl(artistInfoStorage)
        moreDetailsModel = MoreDetailsModelImpl(artistRepository)
    }

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel
}