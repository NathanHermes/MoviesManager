package br.edu.ifsp.ads.moviesmanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie(
  @PrimaryKey(autoGenerate = true) val id: Int?,
  var name: String,
  var releaseYear: Int,
  var studio: String,
  var duration: Int,
  var watched: Boolean,
  var assessment: Int,
  var gender: Int
) : Parcelable