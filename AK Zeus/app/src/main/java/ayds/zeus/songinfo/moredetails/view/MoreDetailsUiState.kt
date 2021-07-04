package ayds.zeus.songinfo.moredetails.view
import ayds.zeus.songinfo.moredetails.model.entities.Card

data class MoreDetailsUiState(
    var spinnerPosition: Int = 0,
    val artistName: String = "",
    val cardList: List<Card> = listOf(),
) {
    fun getCurrentCard(): Card {
        return cardList[spinnerPosition]
    }
}


