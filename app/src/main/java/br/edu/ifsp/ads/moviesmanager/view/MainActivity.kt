package br.edu.ifsp.ads.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import br.edu.ifsp.ads.moviesmanager.R
import br.edu.ifsp.ads.moviesmanager.adapter.MovieAdapter
import br.edu.ifsp.ads.moviesmanager.controller.MovieController
import br.edu.ifsp.ads.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.ads.moviesmanager.model.Movie

class MainActivity : BaseActivity() {
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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.addMovieMi -> {
        val moviesList = ArrayList<Movie>(movies)
        movieActivityResultLauncher.launch(
          Intent(this, MovieActivity::class.java).putParcelableArrayListExtra(
            EXTRA_MOVIES_LIST,
            moviesList
          )
        )
        true
      }

      else -> false
    }
  }

  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    menuInflater.inflate(R.menu.context_menu_main, menu)
  }

  fun updateMovies(_movies: MutableList<Movie>) {
    movies.clear()
    movies.addAll(_movies)
    movieAdapter.notifyDataSetChanged()
  }
}