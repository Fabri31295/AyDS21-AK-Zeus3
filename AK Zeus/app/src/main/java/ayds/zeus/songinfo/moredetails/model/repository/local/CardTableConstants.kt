package ayds.zeus.songinfo.moredetails.model.repository.local

const val ARTISTS_TABLE = "artists"
const val ID_COLUMN = "id"
const val ARTIST_COLUMN = "artist"
const val INFO_COLUMN = "info"
const val URL_COLUMN = "url"
const val SOURCE_COLUMN = "source"
const val LOGO_URL_COLUMN = "logo"
const val CREATE_ARTISTS_TABLE: String =
    "create table $ARTISTS_TABLE (" +
            " $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
            " $ARTIST_COLUMN string," +
            " $INFO_COLUMN string," +
            " $URL_COLUMN string," +
            " $SOURCE_COLUMN int," +
            " $LOGO_URL_COLUMN string)"