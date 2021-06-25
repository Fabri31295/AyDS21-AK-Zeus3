package ayds.zeus.songinfo.moredetails.model
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.zeus.songinfo.moredetails.model.repository.CardRepository
import ayds.zeus.songinfo.moredetails.model.entities.Card

interface MoreDetailsModel {

    fun searchCard(artistName: String)

    fun cardObservable(): Observable<List<Card>>
}

internal class MoreDetailsModelImpl(private val repository: CardRepository) : MoreDetailsModel {

    private val cardSubject = Subject<List<Card>>()

    override fun searchCard(artistName: String){
        cardSubject.notify(repository.getCardList(artistName))
    }

    override fun cardObservable(): Observable<List<Card>> = cardSubject
}