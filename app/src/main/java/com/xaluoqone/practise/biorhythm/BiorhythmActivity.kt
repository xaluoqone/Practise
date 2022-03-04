package com.xaluoqone.practise.biorhythm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setMargins
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.widget.button
import com.xaluoqone.practise.widget.linear

class BiorhythmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            linear {
                orientation = LinearLayoutCompat.VERTICAL
                val biorhythmView =BiorhythmView(this@BiorhythmActivity).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).also {
                        it.setMargins(30.dpi)
                    }
                }
                addView(biorhythmView)
                button {
                    text = getString(R.string.add_node)
                    layoutParams =
                        LinearLayoutCompat.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).also {
                            it.marginStart = 6.dpi
                            it.marginEnd = 6.dpi
                        }

                    setOnClickListener {
                        biorhythmView.addTimeNode()
                    }
                }
            }
        )
    }
}