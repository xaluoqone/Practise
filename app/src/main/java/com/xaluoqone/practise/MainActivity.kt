package com.xaluoqone.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xaluoqone.practise.biorhythm.BiorhythmActivity
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.exo.ExoPlayerActivity
import com.xaluoqone.practise.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            linear {
                orientation = vertical
                button {
                    text = getString(R.string.biorhythm)
                    layoutParams = layoutParams {
                        width = match
                        marginStart = 6.dpi
                        marginEnd = 6.dpi
                    }

                    setOnClickListener {
                        startActivity(Intent(this@MainActivity, BiorhythmActivity::class.java))
                    }
                }

                button {
                    text = getString(R.string.exo_player)
                    layoutParams = layoutParams {
                        width = match
                        marginStart = 6.dpi
                        marginEnd = 6.dpi
                    }

                    setOnClickListener {
                        startActivity(Intent(this@MainActivity, ExoPlayerActivity::class.java))
                    }
                }
            }
        )
    }
}