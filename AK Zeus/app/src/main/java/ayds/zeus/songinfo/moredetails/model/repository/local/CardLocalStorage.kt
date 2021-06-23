package ayds.zeus.songinfo.moredetails.model.repository.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.zeus.songinfo.moredetails.model.entities.Card

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "cards.db"

interface CardLocalStorage {
    fun saveCard(card: Card, artistName: String)
    fun getCard(artistName: String): Card?
}

internal class CardLocalStorageImpl(
    context: Context,
    private val cursorToCardMapper: CursorToCardMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    CardLocalStorage {

    override fun saveCard(card: Card, artistName: String) {
        val contentValues = getCardContentValues(artistName, card)
        this.writableDatabase.insert(CARD_TABLE, null, contentValues)
    }

    override fun getCard(artistName: String): Card? {
        val cursor = getNewCardCursor(artistName)
        return cursorToCardMapper.map(cursor)
    }

    private fun getCardContentValues(artist: String, card: Card) =
        ContentValues().apply {
            this.put(ARTIST_COLUMN, artist)
            this.put(INFO_COLUMN, card.info)
            this.put(URL_COLUMN, card.url)
            this.put(SOURCE_COLUMN, card.source.ordinal)
            this.put(LOGO_URL_COLUMN,card.logoUrl)
        }

    private fun getNewCardCursor(artist: String): Cursor {
        val dataBase = this.readableDatabase
        val projection = arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN, URL_COLUMN, SOURCE_COLUMN, LOGO_URL_COLUMN)
        val selection = "$ARTIST_COLUMN = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "$ARTIST_COLUMN DESC"
        return dataBase.query(
            CARD_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ARTISTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}