package ayds.zeus.songinfo.moredetails.view
import ayds.zeus.songinfo.moredetails.model.entities.Card

data class MoreDetailsUiState(
    val artistName: String = "",
    val cardList: List<Card> = listOf(),
)