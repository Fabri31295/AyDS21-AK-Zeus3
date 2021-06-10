package ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia

import android.database.Cursor
import ayds.zeus.songinfo.moredetails.model.entities.WikipediaCard
import java.sql.SQLException

interface CursorToCardMapper {
    fun map(cursor: Cursor): WikipediaCard?
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): WikipediaCard? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    WikipediaCard(
                        info = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        url = getString(getColumnIndexOrThrow(URL_COLUMN))
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }

}