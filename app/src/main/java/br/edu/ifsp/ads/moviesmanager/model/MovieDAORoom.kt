package br.edu.ifsp.ads.moviesmanager.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MovieDAORoom : RoomDatabase() {
  companion object Constants {
    const val MOVIE_DATABASE_FILE = "movie_room"
  }

  abstract fun getMovieDAO(): MovieDAO
}