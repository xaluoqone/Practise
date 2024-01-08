package com.xaluoqone.practise.large

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.xaluoqone.practise.widget.linear
import com.xaluoqone.practise.widget.match

class LargeImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            linear {
                val largeImageView = LargeImageView(this@LargeImageActivity).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(match, match)
                }
                largeImageView.setSource(assets.open("big.png"))
                lifecycle.addObserver(largeImageView)
                addView(largeImageView)
            }
        )
    }
}