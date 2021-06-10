package ayds.zeus.songinfo.moredetails.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.zeus.songinfo.moredetails.model.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.ArticleRepository

interface MoreDetailsModel {

    fun searchArticle(artistName: String)

    fun articleObservable(): Observable<Card>
}

internal class MoreDetailsModelImpl(private val repository: ArticleRepository) : MoreDetailsModel {

    private val articleSubject = Subject<Card>()

    override fun searchArticle(artistName: String){
        repository.getArticle(artistName).let {
            articleSubject.notify(it)
        }
    }

    override fun articleObservable(): Observable<Card> = articleSubject
}