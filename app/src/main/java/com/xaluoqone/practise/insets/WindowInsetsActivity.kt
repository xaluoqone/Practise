package com.xaluoqone.practise.insets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.doOnApplyWindowInsets
import com.xaluoqone.practise.widget.linear
import com.xaluoqone.practise.widget.text
import com.xaluoqone.practise.widget.vertical

class WindowInsetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ll = linear {
            orientation = vertical
            setBackgroundColor(0xff00ff00.toInt())

            doOnApplyWindowInsets { insets, _, _ ->
                /*val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                updatePadding(top = statusBarInsets.top)*/
                val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
                updatePadding(bottom = navigationBarInsets.bottom)
            }

            addView(text {
                text = "WindowInsetsActivity"
                setBackgroundColor(0xffffffff.toInt())
                doOnApplyWindowInsets { insets, _, _ ->
                    val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                    updatePadding(top = statusBarInsets.top)
                }
            })
            addView(text { text = "WindowInsetsActivity" })
            addView(text { text = "WindowInsetsActivity" })
            addView(text { text = "WindowInsetsActivity" })
            addView(text { text = "WindowInsetsActivity" })
            addView(text { text = "WindowInsetsActivity" })
        }

        setContentView(
            ll
        )

        configSystemBar {
            statusBarBlackFont = true
        }
    }
}