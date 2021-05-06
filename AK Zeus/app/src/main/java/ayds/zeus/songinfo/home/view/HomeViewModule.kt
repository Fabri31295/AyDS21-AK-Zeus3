package ayds.zeus.songinfo.home.view

import ayds.zeus.songinfo.home.controller.HomeControllerModule
import ayds.zeus.songinfo.home.model.HomeModelModule

object HomeViewModule {

    private val factorySongRelease: FactorySongReleaseDateMapper = FactorySongReleaseDateMapperImp()
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(factorySongRelease)

    fun init(homeView: HomeView) {
        HomeModelModule.initHomeModel(homeView)
        HomeControllerModule.onViewStarted(homeView)
    }
}