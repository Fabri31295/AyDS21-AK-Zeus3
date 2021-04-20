package ayds.zeus.songinfo.home.controller

import ayds.zeus.songinfo.home.model.HomeModelModule
import ayds.zeus.songinfo.home.view.HomeView

object HomeControllerModule {

    fun onViewStarted(homeView: HomeView) {
        HomeControllerImpl(HomeModelModule.getHomeModel()).apply {
            setHomeView(homeView)
        }
    }
}