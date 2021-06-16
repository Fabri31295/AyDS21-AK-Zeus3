package ayds.zeus.songinfo.moredetails.model.repository.local

import android.database.Cursor
import ayds.zeus.songinfo.moredetails.model.repository.Source
import ayds.zeus.songinfo.moredetails.model.repository.entities.Card
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.INFO_COLUMN
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.LOGO_URL_COLUMN
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.SOURCE_COLUMN
import ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia.URL_COLUMN
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
                        source = Source.valueOf(getInt(getColumnIndexOrThrow(SOURCE_COLUMN)).toString()),
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