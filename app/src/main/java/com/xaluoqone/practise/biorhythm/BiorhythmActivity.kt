package com.xaluoqone.practise.biorhythm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.xaluoqone.practise.R

class BiorhythmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biorhythm)

        val biorhythm = findViewById<BiorhythmView>(R.id.biorhythm)
        val addTimeNode = findViewById<Button>(R.id.addTimeNode)

        addTimeNode.setOnClickListener {
            biorhythm.addTimeNode()
        }
    }
}