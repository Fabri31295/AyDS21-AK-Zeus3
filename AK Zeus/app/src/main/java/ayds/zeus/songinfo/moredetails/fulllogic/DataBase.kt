package ayds.zeus.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DataBase(private val context: Context): SQLiteOpenHelper(context,"dictionary.db", null, 1) {

    fun saveArtist(dbHelper: DataBase, artist: String, info: String) {
        val dataBase = dbHelper.writableDatabase
        val contentValues = createArtistContentValues(artist, info)
        dataBase.insert("artists", null, contentValues)
    }

    fun getInfo(dbHelper: DataBase, artist: String): String? {
        val cursor = getNewArtistCursor(dbHelper, artist)
        val items = getCursorItems(cursor)
        cursor.close()
        return if (items.isEmpty())
            null
        else
            items[0]
    }

    private fun createArtistContentValues(artist: String, info: String) = ContentValues().apply {
        this.put("artist", artist)
        this.put("info", info)
        this.put("source", 1)
    }

    private fun getNewArtistCursor(dbHelper: DataBase, artist: String): Cursor {
        val dataBase = dbHelper.readableDatabase
        val projection = arrayOf("id", "artist", "info")
        val selection = "artist = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "artist DESC"
        return dataBase.query(
                "artists",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        )
    }

    private fun getCursorItems(cursor: Cursor) = ArrayList<String>().apply {
        while (cursor.moveToNext()){
            val columnIndex = cursor.getColumnIndexOrThrow("info")
            val info = cursor.getString(columnIndex)
            this.add(info)
        }
    }


    override fun onCreate(db: SQLiteDatabase){
        db.execSQL(
                "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)")
        Log.i("DB", "DB created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){}
}