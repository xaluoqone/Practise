package com.xaluoqone.practise.data

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.ex.launchAndRepeatWithViewLifecycle
import com.xaluoqone.practise.widget.*
import kotlinx.coroutines.launch

class DataStoreActivity : AppCompatActivity() {
    private val viewBinding by lazy {
        getContentView()
    }

    private val viewModel by viewModels<DataStoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configSystemBar {
            statusBarBlackFont = true
            applyStatusBarView = viewBinding.root
        }
        setContentView(viewBinding.root)

        initListener()
    }

    private fun getContentView(): ViewBinding<View> {
        val viewMap = mutableMapOf<String, View>()
        val root = linear {
            orientation = vertical
            val text = text {}
            val button = button {
                this.text = "save text"

                layoutParams {
                    width = match
                    marginStart = 6.dpi
                    marginEnd = 6.dpi
                }

                setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.saveText()
                    }
                }
            }
            addView(text)
            addView(button)
            viewMap["text"] = text
            viewMap["button"] = button
        }
        return ViewBinding(root, viewMap)
    }

    private fun initListener() {
        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collect {
                viewBinding.get<TextView>("text").text = it.text
            }
        }
    }

    data class ViewBinding<T : View>(val root: T, val viewMap: Map<String, T>) {
        @Suppress("UNCHECKED_CAST")
        operator fun <T : View> get(key: String): T {
            return viewMap[key] as T
        }
    }
}