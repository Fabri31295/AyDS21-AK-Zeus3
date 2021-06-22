package ayds.zeus.songinfo.moredetails.model.repository.local

import android.database.Cursor
import ayds.zeus.songinfo.moredetails.model.repository.entities.Source
import ayds.zeus.songinfo.moredetails.model.repository.entities.Card
import java.sql.SQLException

interface CursorToCardMapper {
    fun map(cursor: Cursor): Card?
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): Card? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    Card(
                        info = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        url = getString(getColumnIndexOrThrow(URL_COLUMN)),
                        logoUrl = getString(getColumnIndexOrThrow(LOGO_URL_COLUMN)),
                        source = Source.values()[getInt(getColumnIndexOrThrow(SOURCE_COLUMN))],
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