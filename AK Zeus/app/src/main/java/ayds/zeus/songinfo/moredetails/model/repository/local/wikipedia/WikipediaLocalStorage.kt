package ayds.zeus.songinfo.moredetails.model.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.zeus.songinfo.moredetails.model.entities.WikipediaArticle

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "dictionary.db"
private const val ARTISTS_TABLE = "artists"
private const val ID_COLUMN = "id"
private const val ARTIST_COLUMN = "artist"
private const val INFO_COLUMN = "info"
private const val SOURCE_COLUMN = "source"
private const val CREATE_ARTISTS_TABLE: String =
    "create table $ARTISTS_TABLE (" +
            " $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
            " $ARTIST_COLUMN string," +
            " $INFO_COLUMN string," +
            " $SOURCE_COLUMN integer)"

interface WikipediaLocalStorage {
    fun saveArtist(article: WikipediaArticle)
    fun getInfo(artist: String): String?
}

internal class WikipediaLocalStorageImpl(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    WikipediaLocalStorage {

    override fun saveArtist(article: WikipediaArticle) {
        val contentValues = article.info?.let { getArtistContentValues(article.name, it) }
        this.writableDatabase.insert(ARTISTS_TABLE, null, contentValues)
    }

    override fun getInfo(artist: String): String? {
        val cursor = getNewArtistCursor(artist)
        val items = getInfoItems(cursor)
        cursor.close()
        return items.firstOrNull()
    }

    private fun getArtistContentValues(artist: String, info: String) = ContentValues().apply {
        this.put(ARTIST_COLUMN, artist)
        this.put(INFO_COLUMN, info)
        this.put(SOURCE_COLUMN, 1)
    }

    private fun getNewArtistCursor(artist: String): Cursor {
        val dataBase = this.readableDatabase
        val projection = arrayOf(ID_COLUMN, ARTIST_COLUMN, INFO_COLUMN)
        val selection = "$ARTIST_COLUMN = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "$ARTIST_COLUMN DESC"
        return dataBase.query(
                ARTISTS_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        )
    }

    private fun getInfoItems(cursor: Cursor) = ArrayList<String>().apply {
        while (cursor.moveToNext()){
            val columnIndex = cursor.getColumnIndexOrThrow(INFO_COLUMN)
            val info = cursor.getString(columnIndex)
            this.add(info)
        }
    }

    override fun onCreate(db: SQLiteDatabase){
        db.execSQL(CREATE_ARTISTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){}
}