package ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia

import ayds.zeus.songinfo.moredetails.model.repository.external.wikipedia.services.WikipediaServiceModule

object WikipediaModule {
    val wikipediaService: WikipediaService = WikipediaServiceModule.wikipediaService
}