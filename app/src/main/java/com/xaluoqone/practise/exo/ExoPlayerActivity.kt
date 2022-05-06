package com.xaluoqone.practise.exo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.widget.*

class ExoPlayerActivity : AppCompatActivity() {
    private val player by lazy {
        ExoPlayer.Builder(this).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configSystemBar {  }
        setContentView(
            linear {
                orientation = vertical
                addView(StyledPlayerView(this@ExoPlayerActivity).apply {
                    layoutParams {
                        width = match
                        height = wrap
                    }
                    player = this@ExoPlayerActivity.player
                })
            }
        )

        val mediaItem = MediaItem.fromUri("https://xaluoqone.com/files/video/benxi.mp4")

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}