package br.edu.ifsp.ads.moviesmanager.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.moviesmanager.R
import br.edu.ifsp.ads.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.ads.moviesmanager.model.Movie

class MovieAdapter(context: Context, private val movies: MutableList<Movie>) :
  ArrayAdapter<Movie>(context, R.layout.tile_movie, movies) {
  private lateinit var tileMovieBinding: TileMovieBinding

  @SuppressLint("SetTextI18n")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val movie: Movie = movies[position]
    var tileMovieView = convertView

    if (tileMovieView == null) {
      tileMovieBinding = TileMovieBinding.inflate(
        context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
        parent, false
      )
      tileMovieView = tileMovieBinding.root

      val tileMovieViewHolder = TileMovieViewHolder(
        tileMovieView.findViewById(R.id.nameTv),
        tileMovieView.findViewById(R.id.durationTv)
      )

      tileMovieView.tag = tileMovieViewHolder
    }

    with(tileMovieView.tag as TileMovieViewHolder) {
      nameTv.text = movie.name
      durationTv.text = "${movie.duration} minutos"
    }

    return tileMovieView
  }

  private data class TileMovieViewHolder(
    val nameTv: TextView,
    val durationTv: TextView
  )
}