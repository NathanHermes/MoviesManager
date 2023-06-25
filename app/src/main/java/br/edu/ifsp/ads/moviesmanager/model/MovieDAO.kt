package br.edu.ifsp.ads.moviesmanager.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDAO {
  @Query("SELECT * FROM Movie ORDER BY name")
  fun findMoviesOrderByName(): MutableList<Movie>

  @Query("SELECT * FROM Movie ORDER BY releaseYear")
  fun findMoviesOrderByReleaseYear(): MutableList<Movie>

  @Insert
  fun create(movie: Movie)

  @Update
  fun update(movie: Movie)

  @Delete
  fun delete(movie: Movie)
}