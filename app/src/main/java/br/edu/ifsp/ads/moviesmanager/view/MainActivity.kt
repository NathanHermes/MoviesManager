package br.edu.ifsp.ads.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.moviesmanager.adapter.MovieAdapter
import br.edu.ifsp.ads.moviesmanager.controller.MovieController
import br.edu.ifsp.ads.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.moviesmanager.model.Movie

class MainActivity : AppCompatActivity() {
  private val movies: MutableList<Movie> = mutableListOf()
  private lateinit var movieActivityResultLauncher: ActivityResultLauncher<Intent>
  private val amb: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }
  private val movieAdapter: MovieAdapter by lazy {
    MovieAdapter(this, movies)
  }
  private val movieController: MovieController by lazy {
    MovieController(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(amb.root)

    with(amb) {
      movieController.findAllMovieOrderByName()
      moviesLv.adapter = movieAdapter
    }
  }

  fun updateMovies(_movies: MutableList<Movie>) {
    movies.clear()
    movies.addAll(_movies)
    movieAdapter.notifyDataSetChanged()
  }
}