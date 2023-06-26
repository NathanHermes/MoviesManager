package br.edu.ifsp.ads.moviesmanager.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import br.edu.ifsp.ads.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.ads.moviesmanager.model.Movie

class MovieActivity : BaseActivity() {
  private val amb: ActivityMovieBinding by lazy {
    ActivityMovieBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(amb.root)

    val receiveMovie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
    } else {
      intent.getParcelableExtra(EXTRA_MOVIE)
    }

    receiveMovie?.let { setValuesIntoView(it) }

    with(amb) {
      backBt.setOnClickListener { finish() }

      saveBt.setOnClickListener {
        val movie: Movie? = getValuesFromView(receiveMovie?.id)

        if (movie != null) {
          val receiveMovies = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(EXTRA_MOVIES_LIST, Movie::class.java)
          } else {
            intent.getParcelableArrayListExtra(EXTRA_MOVIES_LIST)
          }

          val position = receiveMovies?.let { _movie ->
            _movie.indexOfFirst { it.name == movie.name }
          }

          if (receiveMovie == null && position != -1) {
            Toast.makeText(
              this@MovieActivity,
              "Já existe um filme com esse nome",
              Toast.LENGTH_SHORT
            ).show()
          } else {
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)

            setResult(RESULT_OK, resultIntent)
            finish()
          }
        }
      }
    }
  }

  private fun validateInputs(): String {
    with(amb) {
      var inputErrors = ""

      if (nameEt.text.isBlank()) inputErrors += "O campo nome é obrigatório\n"
      if (releaseYearEt.text.isBlank()) inputErrors += "O campo ano de lançamento é obrigatório\n"
      if (studioEt.text.isBlank()) inputErrors += "O campo estúdio/produtora é obrigatório\n"
      if (durationEt.text.isBlank()) inputErrors += "O campo duração é obrigatório"

      return inputErrors
    }
  }

  private fun getValuesFromView(id: Int?): Movie? {
    with(amb) {
      val errors = validateInputs()
      if (errors.isNotBlank()) {
        Toast.makeText(this@MovieActivity, errors, Toast.LENGTH_SHORT).show()
        return null
      }

      val name: String = nameEt.text.toString()
      val releaseYear: Int = releaseYearEt.text.toString().toInt()
      val studio: String = studioEt.text.toString()
      val duration: Int = durationEt.text.toString().toInt()
      val watched: Boolean = watchedRb.isChecked
      val assessment: Int =
        if (assessmentEt.text.isBlank()) 0 else assessmentEt.text.toString().toInt()
      val genderId: Int = gendersSp.selectedItemPosition

      return Movie(id, name, releaseYear, studio, duration, watched, assessment, genderId)
    }
  }

  private fun setValuesIntoView(movie: Movie) {
    val viewMovie = intent.getBooleanExtra(EXTRA_VIEW_MOVIE, false)
    with(amb) {
      if (viewMovie) {
        titleMovieTv.text = "Informações do filme"
      } else {
        titleMovieTv.text = "Editar filme"
      }

      nameEt.setText(movie.name)
      nameEt.isEnabled = false

      releaseYearEt.setText(movie.releaseYear.toString())
      releaseYearEt.isEnabled = !viewMovie
      studioEt.setText(movie.studio)
      studioEt.isEnabled = !viewMovie
      durationEt.setText(movie.duration.toString())
      durationEt.isEnabled = !viewMovie
      if (movie.watched) watchedRb.isChecked = true
      else notWatchRb.isChecked = true
      watchedRb.isEnabled = !viewMovie
      notWatchRb.isEnabled = !viewMovie
      assessmentEt.setText(movie.assessment.toString())
      assessmentEt.isEnabled = !viewMovie
      gendersSp.setSelection(movie.gender)
      gendersSp.isEnabled = !viewMovie

      saveBt.visibility = if (viewMovie) GONE else VISIBLE
    }
  }
}