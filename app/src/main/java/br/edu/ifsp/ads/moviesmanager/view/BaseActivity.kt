package br.edu.ifsp.ads.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity

sealed class BaseActivity : AppCompatActivity() {
  protected val EXTRA_MOVIE = "Movie"
  protected val EXTRA_MOVIES_LIST = "MoviesList"
  protected val EXTRA_VIEW_MOVIE = "ViewMovie"
}