package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.controller.HomeControllerModule
import ayds.zeus.songinfo.home.model.HomeModelModule

object HomeViewModule {

    val song_to_release_date_string_mapper: SongToReleaseDateStringMapper = SongToReleaseDateStringMapperImpl()
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(song_to_release_date_string_mapper)

    fun init(homeView: HomeView) {
        HomeModelModule.initHomeModel(homeView)
        HomeControllerModule.onViewStarted(homeView)
    }
}