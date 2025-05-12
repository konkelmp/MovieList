package example.com.movielist

class Movie(val title : String?, val year : String?, val genre : String?, val rating : String?) {
    override fun toString(): String {
        return "Title = " + title + "Year = " + year + "Genre = " + genre + "Rating = " + rating
    }
    fun convertOut() : String {
        return "$title,$year,$genre,$rating"
    }
}