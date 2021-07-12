package ayds.zeus.songinfo.moredetails.model.entities

enum class Source(val sourceName: String){
    WIKIPEDIA("Wikipedia.org"),
    LASTFM("Last.fm"),
    NYTIMES("Nytimes.com"),
    EMPTY("Invalid source")
}