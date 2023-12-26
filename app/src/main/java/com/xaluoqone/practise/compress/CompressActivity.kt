package com.xaluoqone.practise.compress

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.doOnApplyWindowInsets
import com.xaluoqone.practise.widget.button
import com.xaluoqone.practise.widget.linear
import com.xaluoqone.practise.widget.vertical
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompressActivity : AppCompatActivity() {
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
                addView(button("压缩图片") {
                    lifecycleScope.launch(Dispatchers.IO) {
                        // 将压缩后的文件储存到沙盒路径下的files目录
                        val path = getExternalFilesDir("compressImages")?.absolutePath + "/keqing_compress.jpg"
                        val bitmap = BitmapFactory.decodeResource(
                            resources,
                            R.mipmap.keqing,
                            BitmapFactory.Options().apply {
                                inScaled = false
                            }
                        )
                        Log.i("Bitmap size", "width = ${bitmap.width}, height = ${bitmap.height}")
                        NativeCompress.compressBitmap(bitmap, 20, path)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@CompressActivity, "压缩成功", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        )
    }
}