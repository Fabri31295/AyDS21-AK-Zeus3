package ayds.zeus.songinfo.home.model

import android.content.Context
import ayds.zeus.songinfo.home.model.repository.SongRepository
import ayds.zeus.songinfo.home.model.repository.SongRepositoryImpl
import ayds.zeus.songinfo.home.model.repository.external.spotify.SpotifyModule
import ayds.zeus.songinfo.home.model.repository.external.spotify.SpotifyTrackService
import ayds.zeus.songinfo.home.model.repository.local.spotify.SpotifyLocalStorage
import ayds.zeus.songinfo.home.model.repository.local.spotify.sqldb.CursorToSpotifySongMapperImpl
import ayds.zeus.songinfo.home.model.repository.local.spotify.sqldb.SpotifyLocalStorageImpl
import ayds.zeus.songinfo.home.view.HomeView

object HomeModelModule {

    private lateinit var homeModel: HomeModel

    fun getHomeModel(): HomeModel = homeModel

    fun initHomeModel(homeView: HomeView) {
        val spotifyLocalStorage: SpotifyLocalStorage = SpotifyLocalStorageImpl(
          homeView as Context, CursorToSpotifySongMapperImpl()
        )
        val spotifyTrackService: SpotifyTrackService = SpotifyModule.spotifyTrackService

        val repository: SongRepository =
            SongRepositoryImpl(spotifyLocalStorage, spotifyTrackService)

        homeModel = HomeModelImpl(repository)
    }
}