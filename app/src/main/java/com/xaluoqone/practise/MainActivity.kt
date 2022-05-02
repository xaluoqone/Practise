package com.xaluoqone.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.xaluoqone.practise.biorhythm.BiorhythmActivity
import com.xaluoqone.practise.ex.applyStatusBarPadding
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.ex.setStatusBarTextColorIsLight
import com.xaluoqone.practise.ex.setStatusBarTransparent
import com.xaluoqone.practise.exo.ExoPlayerActivity
import com.xaluoqone.practise.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent()
        setStatusBarTextColorIsLight(true)
        findViewById<FrameLayout>(android.R.id.content).applyStatusBarPadding()
        setContentView(
            linear {
                orientation = vertical
                button {
                    text = getString(R.string.biorhythm)
                    layoutParams {
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
                    layoutParams {
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