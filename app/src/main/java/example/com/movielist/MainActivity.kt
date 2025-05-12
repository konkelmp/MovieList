/**
 * Preston Konkel
 * CS3013
 * Professor Elliot Evans
 * Major Program 3 - MovieList
 *
 * MovieList is an app where the user can see an initial list of the
 * top 20 movies. The user can scroll through this list of movies as
 * well as add movies, and an option to save. The add option sends the
 * user to a second activty where there are four EditTexts to enter the
 * details of the movie and then a button to sumbit it. The user can
 * also use the a swipe right action to remove any movie from the list.
 */

package example.com.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    val movieList : MutableList<Movie?> = ArrayList<Movie?>()
    val movieAdapter = MovieAdapter(movieList as MutableList<Movie>)
    var myPlace: String? = null

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val data = activityResult.data
            val title = data?.getStringExtra("title") ?: ""
            val year = data?.getStringExtra("year") ?: ""
            val genre = data?.getStringExtra("genre") ?: ""
            val rating = data?.getStringExtra("rating") ?: ""
            movieList.add(Movie(title, year, genre, rating))
            movieAdapter.notifyDataSetChanged()
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*
        movieList.add(Movie("28 Days Later", "2002", "Horror/Thriller", "8.5"))
        movieList.add(Movie("Do the Right Thing", "1989", "Comedy/Drama", "9.9"))
        movieList.add(Movie("Minecraft Movie", "2025", "Comedy", "2.9"))
         */

        //fileIO
        val myDir = this.getFilesDir()
        val myDirName = myDir.getAbsolutePath()
        myPlace = myDirName

        readFile()

        //recycler
        val recyclerView = findViewById<RecyclerView?>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        //swipe delete
        val itemTouchHelper = ItemTouchHelper(movieAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.setAdapter(movieAdapter)
    }

    fun startSecond(v: View) {
        startForResult.launch(Intent(this, AddMovieActivity::class.java))
    }

    //Function readFile - reads the file MOVIELIST.csv - populating movie list
    fun readFile() {
        Log.d("FileIO", "Entered readFile()")
        try {
            val f = File(myPlace + "/MOVIELIST.csv")
            val myReader = Scanner(f)
            while (myReader.hasNextLine()) {
                val data = myReader.nextLine()
                val parts = data.split(",")
                Log.d("FileIO", "Reached movieList.add()")
                movieList.add(Movie(parts[0], parts[1], parts[2], parts[3]))
            }
            myReader.close()
        }
        catch (e: IOException) {
            println("An error occurred reading the file")
            e.printStackTrace()
        }
    }

    fun writeFile(v: View) {
        Log.d("FileIO", "Entered writeFile()")
        try {
            val f = File(myPlace + "/MOVIELIST.csv")

            if (f.exists()) {
                Log.d("FileIO", "File exists")
            } else {
                Log.d("FileIO", "File DNE")
            }

            val fw = FileWriter(f, false) //overwrite instead of append
            val count = movieList.size
            Log.d("FileIO", "Count = " + count)
            //print the list
            for (m : Movie? in movieList) {
                val s = m.toString()
                Log.d("FileIO", s)
                fw.write(m?.convertOut() + "\n")
            }
            fw.flush()
            fw.close()
        } catch (e: IOException) {
            Log.d("FileIO", "Exception \n e")
        }
    }


}