package com.xaluoqone.practise.biorhythm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setMargins
import androidx.core.view.updatePadding
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.doOnApplyWindowInsets
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.widget.*

class BiorhythmActivity : AppCompatActivity() {

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
                val biorhythmView = BiorhythmView(this@BiorhythmActivity).apply {
                    layoutParams<LinearLayoutCompat.LayoutParams> {
                        width = match
                        setMargins(30.dpi)
                    }
                }
                addView(biorhythmView)
                addView(button {
                    text = getString(R.string.add_node)
                    layoutParams<LinearLayoutCompat.LayoutParams> {
                        width = match
                        marginStart = 6.dpi
                        marginEnd = 6.dpi
                    }

                    setOnClickListener {
                        biorhythmView.addTimeNode()
                    }
                })
            }
        )
    }
}