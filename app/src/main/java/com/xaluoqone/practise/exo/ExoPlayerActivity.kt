package com.xaluoqone.practise.exo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
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
        configSystemBar { }
        setContentView(
            constraint {
                val playerView = playerView()
                val fullScreen = button {
                    text = "全屏"
                    layoutParams<ConstraintLayout.LayoutParams> {
                        width = wrap
                        height = wrap
                        bottomToBottom = playerView.id
                        endToEnd = playerView.id
                    }

                    setOnClickListener {
                        requestedOrientation =
                            if (requestedOrientation == android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            } else {
                                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            }
                    }
                }
                addView(playerView)
                addView(fullScreen)
            }
        )

        val mediaItem = MediaItem.fromUri("https://xaluoqone.com/files/video/benxi.mp4")

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    context (ConstraintLayout)private fun playerView(): StyledPlayerView {
        return StyledPlayerView(this@ExoPlayerActivity).apply {
            id = System.currentTimeMillis().toInt()
            layoutParams<ConstraintLayout.LayoutParams> {
                width = match
                topToTop = PARENT_ID
                dimensionRatio = "16:9"
            }
            player = this@ExoPlayerActivity.player
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}