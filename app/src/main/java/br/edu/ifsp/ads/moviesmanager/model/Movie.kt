package br.edu.ifsp.ads.moviesmanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity
data class Movie(
  @PrimaryKey(autoGenerate = true) val id: Int?,
  var name: String,
  var releaseYear: Date,
  var studio: String,
  var duration: Int,
  var watched: Boolean,
  var assessment: Int,
  var gender: String
) : Parcelable