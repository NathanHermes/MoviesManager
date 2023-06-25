package br.edu.ifsp.ads.moviesmanager.controller

import androidx.room.Room
import br.edu.ifsp.ads.moviesmanager.model.Movie
import br.edu.ifsp.ads.moviesmanager.model.MovieDAO
import br.edu.ifsp.ads.moviesmanager.model.MovieDAORoom
import br.edu.ifsp.ads.moviesmanager.view.MainActivity

class MovieController(private val mainActivity: MainActivity) {
  private val movieDaoImpl: MovieDAO = Room.databaseBuilder(
    mainActivity,
    MovieDAORoom::class.java,
    MovieDAORoom.MOVIE_DATABASE_FILE
  ).build().getMovieDAO()

  fun findAllMovieOrderByName() {
    Thread {
      val movies = movieDaoImpl.findMoviesOrderByName()
      mainActivity.runOnUiThread {
        mainActivity.updateMovies(movies)
      }
    }.start()
  }
  fun findAllMoviesOrderByReleaseYear() {
    Thread {
      val movies = movieDaoImpl.findMoviesOrderByReleaseYear()
      mainActivity.runOnUiThread {
        mainActivity.updateMovies(movies)
      }
    }.start()
  }

  fun save(_movie: Movie) {
    Thread {
      movieDaoImpl.create(_movie)
      findAllMovieOrderByName()
    }.start()
  }
  fun edit(_movie: Movie) {
    Thread {
      movieDaoImpl.update(_movie)
      findAllMovieOrderByName()
    }.start()
  }
  fun delete(_movie: Movie) {
    Thread {
      movieDaoImpl.delete(_movie)
      findAllMovieOrderByName()
    }.start()
  }
}