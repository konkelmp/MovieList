package example.com.movielist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_movie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun backToFirst(v: View) {
        var title = findViewById<EditText>(R.id.title).getText().toString()
        var year = findViewById<EditText>(R.id.year).getText().toString()
        var genre = findViewById<EditText>(R.id.genre).getText().toString()
        var rating = findViewById<EditText>(R.id.rating).getText().toString()

        setBookInfo(title, year, genre, rating)
    }

    fun setBookInfo(title:String, year:String, genre:String, rating:String) {
        var movieInfoIntent = Intent()
        movieInfoIntent.putExtra("title", title)
        movieInfoIntent.putExtra("year", year)
        movieInfoIntent.putExtra("genre", genre)
        movieInfoIntent.putExtra("rating", rating)
        setResult(Activity.RESULT_OK, movieInfoIntent)
        finish()
    }
}