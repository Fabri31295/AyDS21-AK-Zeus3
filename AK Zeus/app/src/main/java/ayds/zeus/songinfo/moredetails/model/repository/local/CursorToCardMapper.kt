package ayds.zeus.songinfo.moredetails.model.repository.local

import android.database.Cursor
import android.util.Log
import ayds.zeus.songinfo.moredetails.model.entities.Source
import ayds.zeus.songinfo.moredetails.model.entities.Card
import java.sql.SQLException

interface CursorToCardMapper {
    fun map(cursor: Cursor): List<Card>
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): List<Card> {
        val cardList = mutableListOf<Card>()
        try {
            var card : Card
            with(cursor) {
                while (moveToNext()) {
                    card =
                        Card(
                            info = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                            url = getString(getColumnIndexOrThrow(URL_COLUMN)),
                            logoUrl = getString(getColumnIndexOrThrow(LOGO_URL_COLUMN)),
                            source = Source.values()[getInt(getColumnIndexOrThrow(SOURCE_COLUMN))],
                        )
                    cardList.add(card)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return cardList
    }

}