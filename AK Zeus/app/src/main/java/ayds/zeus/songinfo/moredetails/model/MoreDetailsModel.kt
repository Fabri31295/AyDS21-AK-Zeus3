package ayds.zeus.songinfo.moredetails.model

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.zeus.songinfo.moredetails.model.repository.CardRepository
import ayds.zeus3.wikipedia.Card

interface MoreDetailsModel {

    fun searchCard(artistName: String)

    fun cardObservable(): Observable<Card>
}

internal class MoreDetailsModelImpl(private val repository: CardRepository) : MoreDetailsModel {

    private val cardSubject = Subject<Card>()

    override fun searchCard(artistName: String){
        repository.getCard(artistName).let {
            cardSubject.notify(it)
        }
    }

    override fun cardObservable(): Observable<Card> = cardSubject
}