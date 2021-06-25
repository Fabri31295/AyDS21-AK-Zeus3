package ayds.zeus.songinfo.moredetails.model.entities

enum class Source(val sourceName: String){
    WIKIPEDIA("From Wikipedia.org"),
    LASTFM("From Last.fm"),
    NYTIMES("From Nytimes.com"),
    EMPTY("Invalid source")
}