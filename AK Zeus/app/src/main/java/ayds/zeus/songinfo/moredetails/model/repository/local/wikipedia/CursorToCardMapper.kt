package ayds.zeus.songinfo.moredetails.model.repository.local.wikipedia

import android.database.Cursor
import ayds.zeus.songinfo.moredetails.model.entities.CardImpl
import java.sql.SQLException

interface CursorToCardMapper {
    fun map(cursor: Cursor): CardImpl?
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): CardImpl? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    CardImpl(
                        info = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        url = getString(getColumnIndexOrThrow(URL_COLUMN)),
                        logo_url = getString(getColumnIndexOrThrow(LOGO_URL_COLUMN)),
                        source = getInt(getColumnIndexOrThrow(SOURCE_COLUMN)),
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