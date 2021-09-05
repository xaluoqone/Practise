package com.xaluoqone.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.xaluoqone.practise.biorhythm.BiorhythmActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var biorhythm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initListener()
    }

    private fun initView() {
        biorhythm = findViewById(R.id.biorhythm)
    }

    private fun initListener() {
        biorhythm.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.biorhythm -> {
                startActivity(Intent(this, BiorhythmActivity::class.java))
            }
        }
    }
}