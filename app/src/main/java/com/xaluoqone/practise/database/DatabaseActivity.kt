package com.xaluoqone.practise.database

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import com.xaluoqone.practise.R
import com.xaluoqone.practise.database.table.TestInfo
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.ex.launchAndRepeatWithViewLifecycle
import com.xaluoqone.practise.ktx.logv
import com.xaluoqone.practise.widget.*
import kotlinx.coroutines.launch

class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = contentView()
        configSystemBar {
            statusBarBlackFont = true
            applyStatusBarView = contentView
        }
        setContentView(contentView)
    }

    private fun contentView(): View {
        val testInfoDao = AppDatabase.instance.testInfoDao()

        launchAndRepeatWithViewLifecycle {
            testInfoDao.queryAllTestInfo().collect {
                logv("$it")
            }
        }

        return linear {
            orientation = vertical

            addView(button(getString(R.string.insert_test_info_list)) {
                lifecycleScope.launch {
                    testInfoDao.insertTestInfoList(
                        listOf(
                            TestInfo(1, "1"),
                            TestInfo(2, "2"),
                            TestInfo(3, "3"),
                            TestInfo(4, "4"),
                            TestInfo(5, "5"),
                        )
                    )
                }
            })

            addView(button(getString(R.string.delete_the_range_test_info)) {
                lifecycleScope.launch {
                    testInfoDao.deleteTestInfo(listOf(2, 3, 4, 5, 6))
                }
            })
        }
    }

    context(LinearLayoutCompat)
    private fun button(text: String, onClick: () -> Unit) = button {
        this.text = text
        layoutParams<LinearLayoutCompat.LayoutParams>(width = match) {
            marginStart = 6.dpi
            marginEnd = 6.dpi
        }

        setOnClickListener {
            onClick()
        }
    }
}