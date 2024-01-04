package com.xaluoqone.practise.compress

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.ktx.getCompressBitmap
import com.xaluoqone.practise.widget.DataAdapter
import com.xaluoqone.practise.widget.adapter
import com.xaluoqone.practise.widget.horizontal
import com.xaluoqone.practise.widget.image
import com.xaluoqone.practise.widget.match
import com.xaluoqone.practise.widget.recycler
import com.xaluoqone.practise.widget.text
import com.xaluoqone.practise.widget.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BitmapMemoryManagerActivity : AppCompatActivity() {
    private val viewModel by viewModels<HeroViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = recycler {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = adapter(
                data = viewModel.uiState.value.data,
                onCreateItem = {
                    orientation = horizontal
                    layoutParams = RecyclerView.LayoutParams(match, wrap).apply {
                        marginStart = 16.dpi
                        topMargin = 6.dpi
                        marginEnd = 16.dpi
                        bottomMargin = 6.dpi
                    }
                    addView(image {
                        layoutParams = LinearLayoutCompat.LayoutParams(96.dpi, 72.dpi)
                    })
                    addView(text {
                        layoutParams = LinearLayoutCompat.LayoutParams(0, wrap).apply {
                            weight = 1f
                            marginStart = 12.dpi
                        }
                        setTextColor(0xFF333333.toInt())
                        textSize = 16f
                    })
                },
                onBindData = { data ->
                    val imageView = this[0] as ImageView
                    val textView = this[1] as TextView
                    val compressBitmap = context.getCompressBitmap(data.image, 96.dpi, 72.dpi, true)
                    imageView.setImageBitmap(compressBitmap)
                    textView.text = data.name
                }
            )

            lifecycleScope.launch {
                viewModel.uiState.collect {
                    (adapter as? DataAdapter<Hero>)?.setData(it.data)
                }
            }
        }
        configSystemBar {
            statusBarBlackFont = true
            applyStatusBarView = content
        }
        setContentView(content)

        viewModel.loadData()
    }
}

class HeroViewModel : ViewModel() {
    val uiState = MutableStateFlow(UiState(emptyList()))

    fun loadData() {
        uiState.value = UiState(
            listOf(
                Hero(
                    name = "阿离",
                    image = R.mipmap.a_li
                ),
                Hero(
                    name = "大乔",
                    image = R.mipmap.da_qiao
                ),
                Hero(
                    name = "火舞",
                    image = R.mipmap.huo_wu
                ),
                Hero(
                    name = "露露",
                    image = R.mipmap.lu_lu
                ),
                Hero(
                    name = "露娜",
                    image = R.mipmap.lu_na
                ),
                Hero(
                    name = "木兰",
                    image = R.mipmap.mu_lan
                ),
                Hero(
                    name = "西施",
                    image = R.mipmap.xi_si
                ),
                Hero(
                    name = "小乔",
                    image = R.mipmap.xiao_qiao
                ),
                Hero(
                    name = "昭君",
                    image = R.mipmap.zhao_jun
                )
            )
        )
    }

    data class UiState(
        val data: List<Hero>
    )
}

data class Hero(
    val name: String,
    val image: Int
)