package com.xaluoqone.practise.biorhythm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.setMargins
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.widget.*

class BiorhythmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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