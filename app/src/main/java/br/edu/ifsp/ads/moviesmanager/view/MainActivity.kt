package br.edu.ifsp.ads.moviesmanager.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

      movieActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
          if (result.resultCode == RESULT_OK) {
            val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
              result.data?.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
            } else {
              result.data?.getParcelableExtra(EXTRA_MOVIE)
            }

            movie?.let { _movie ->
              val position = movies.indexOfFirst { it.id == _movie.id }

              if (position != -1) {
                movies[position] = _movie
                movieController.edit(_movie)
                Toast.makeText(this@MainActivity, "Filme editado com sucesso", Toast.LENGTH_SHORT)
                  .show()
              } else {
                movieController.save(_movie)
                Toast.makeText(
                  this@MainActivity,
                  "Filme cadastrado com sucesso",
                  Toast.LENGTH_SHORT
                ).show()
              }

              movieAdapter.notifyDataSetChanged()
            }
          }
        }

      registerForContextMenu(moviesLv)


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
        val movieIntent = Intent(this, MovieActivity::class.java)
        movieIntent.putParcelableArrayListExtra(EXTRA_MOVIES_LIST, moviesList)
        movieActivityResultLauncher.launch(movieIntent)
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

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
    return when (item.itemId) {
      R.id.editMovieMi -> {
        val movieList = ArrayList<Movie>(movies)
        val movieIntent = Intent(this, MovieActivity::class.java)
        movieIntent.putExtra(EXTRA_MOVIE, movies[position])
        movieIntent.putParcelableArrayListExtra(EXTRA_MOVIES_LIST, movieList)
        movieActivityResultLauncher.launch(movieIntent)
        true
      }

      R.id.deleteMovieMi -> {
        movieController.delete(movies[position])
        Toast.makeText(this, "Filme deletado", Toast.LENGTH_SHORT).show()
        true
      }

      else -> false
    }
  }

  fun updateMovies(_movies: MutableList<Movie>) {
    movies.clear()
    movies.addAll(_movies)
    movieAdapter.notifyDataSetChanged()
  }
}