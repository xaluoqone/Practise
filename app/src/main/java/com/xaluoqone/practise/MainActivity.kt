package com.xaluoqone.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.LinearLayoutCompat.VERTICAL
import com.xaluoqone.practise.biorhythm.BiorhythmActivity
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.widget.button
import com.xaluoqone.practise.widget.linear

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            linear {
                orientation = VERTICAL
                button {
                    text = getString(R.string.biorhythm)
                    layoutParams =
                        LinearLayoutCompat.LayoutParams(MATCH_PARENT, WRAP_CONTENT).also {
                            it.marginStart = 6.dpi
                            it.marginEnd = 6.dpi
                        }

                    setOnClickListener {
                        startActivity(Intent(this@MainActivity, BiorhythmActivity::class.java))
                    }
                }
            }
        )
    }
}