package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.controller.HomeControllerModule
import ayds.zeus.songinfo.home.model.HomeModelModule

object HomeViewModule {

    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl()

    fun init(homeView: HomeView) {
        HomeModelModule.initHomeModel(homeView)
        HomeControllerModule.onViewStarted(homeView)
    }
}