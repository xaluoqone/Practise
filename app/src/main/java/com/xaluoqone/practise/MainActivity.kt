package com.xaluoqone.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.xaluoqone.practise.biorhythm.BiorhythmActivity
import com.xaluoqone.practise.data.DataStoreActivity
import com.xaluoqone.practise.ex.*
import com.xaluoqone.practise.exo.ExoPlayerActivity
import com.xaluoqone.practise.insets.WindowInsetsActivity
import com.xaluoqone.practise.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configSystemBar {
            statusBarBlackFont = true
        }
        setContentView(
            linear {
                orientation = vertical
                doOnApplyWindowInsets { insets, _, _ ->
                    val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                    updatePadding(top = statusBarInsets.top)
                }
                addView(button(getString(R.string.biorhythm)) {
                    startActivity(Intent(this@MainActivity, BiorhythmActivity::class.java))
                })
                addView(button(getString(R.string.exo_player)) {
                    startActivity(Intent(this@MainActivity, ExoPlayerActivity::class.java))
                })
                addView(button(getString(R.string.window_insets)) {
                    startActivity(Intent(this@MainActivity, WindowInsetsActivity::class.java))
                })
                addView(button(getString(R.string.data_store)) {
                    startActivity(Intent(this@MainActivity, DataStoreActivity::class.java))
                })
            }
        )
    }

    context(LinearLayoutCompat)
    private fun button(text: String, onClick: () -> Unit) = button {
        this.text = text
        layoutParams {
            width = match
            marginStart = 6.dpi
            marginEnd = 6.dpi
        }

        setOnClickListener {
            onClick()
        }
    }
}