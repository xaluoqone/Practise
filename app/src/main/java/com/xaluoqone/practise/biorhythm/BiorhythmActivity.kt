package com.xaluoqone.practise.biorhythm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.setMargins
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.applyStatusBarPadding
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.ex.setStatusBarTextColorIsLight
import com.xaluoqone.practise.ex.setStatusBarTransparent
import com.xaluoqone.practise.widget.*

class BiorhythmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent()
        setStatusBarTextColorIsLight(true)
        findViewById<FrameLayout>(android.R.id.content).applyStatusBarPadding()
        setContentView(
            linear {
                orientation = vertical
                val biorhythmView = BiorhythmView(this@BiorhythmActivity).apply {
                    layoutParams {
                        width = match
                        setMargins(30.dpi)
                    }
                }
                addView(biorhythmView)
                button {
                    text = getString(R.string.add_node)
                    layoutParams {
                        width = match
                        marginStart = 6.dpi
                        marginEnd = 6.dpi
                    }

                    setOnClickListener {
                        biorhythmView.addTimeNode()
                    }
                }
            }
        )
    }
}