package com.xaluoqone.practise.proxy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.doOnApplyWindowInsets
import com.xaluoqone.practise.widget.button
import com.xaluoqone.practise.widget.linear
import com.xaluoqone.practise.widget.vertical

class ProxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configSystemBar {
            statusBarBlackFont = true
        }

        setContentView(
            linear {
                orientation = vertical
                doOnApplyWindowInsets { insets, _, _ ->
                    val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                    updatePadding(top = statusBarInsets.top)
                }
                val features = FeatureImpl.create()
                addView(
                    button("fn1") {
                        features.fn1("这是一个参数")
                    }
                )
                addView(
                    button("fn2") {
                        features.fn2()
                    }
                )
                addView(
                    button("fn3") {
                        features.fn3()
                    }
                )
                addView(
                    button("fn4") {
                        features.fn4()
                    }
                )
            }
        )
    }
}