package ayds.zeus.songinfo.moredetails.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.zeus.songinfo.moredetails.model.entities.Article
import ayds.zeus.songinfo.moredetails.model.repository.ArticleRepository

interface MoreDetailsModel {

    fun searchArticle(artistName: String)

    fun articleObservable(): Observable<Article>
}

internal class MoreDetailsModelImpl(private val repository: ArticleRepository) : MoreDetailsModel {

    private val articleSubject = Subject<Article>()

    override fun searchArticle(artistName: String){
        repository.getArticle(artistName).let {
            articleSubject.notify(it)
        }
    }

    override fun articleObservable(): Observable<Article> = articleSubject
}