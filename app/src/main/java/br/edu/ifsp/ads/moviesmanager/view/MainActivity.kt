package br.edu.ifsp.ads.moviesmanager.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.moviesmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private val amb: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(amb.root)
  }
}