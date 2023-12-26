package com.xaluoqone.practise

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.xaluoqone.practise.biorhythm.BiorhythmActivity
import com.xaluoqone.practise.compress.CompressActivity
import com.xaluoqone.practise.data.DataStoreActivity
import com.xaluoqone.practise.database.DatabaseActivity
import com.xaluoqone.practise.event.EventActivity
import com.xaluoqone.practise.event.FlowEventBus
import com.xaluoqone.practise.event.onBroadcast
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.doOnApplyWindowInsets
import com.xaluoqone.practise.exo.ExoPlayerActivity
import com.xaluoqone.practise.insets.WindowInsetsActivity
import com.xaluoqone.practise.proxy.ProxyActivity
import com.xaluoqone.practise.recycler.RecyclerActivity
import com.xaluoqone.practise.widget.button
import com.xaluoqone.practise.widget.linear
import com.xaluoqone.practise.widget.vertical
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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
                addView(button(getString(R.string.biorhythm)) {
                    startActivity(Intent(this@MainActivity, BiorhythmActivity::class.java))
                })
                addView(button(getString(R.string.exo_player)) {
                    startActivity(Intent(this@MainActivity, ExoPlayerActivity::class.java))
                })
                addView(button(getString(R.string.window_insets)) {
                    startActivity(Intent(this@MainActivity, WindowInsetsActivity::class.java))
                })
                addView(button(getString(R.string.data_store)) {
                    startActivity(Intent(this@MainActivity, DataStoreActivity::class.java))
                })
                addView(button(getString(R.string.flow_event_bus)) {
                    startActivity(Intent(this@MainActivity, EventActivity::class.java))
                })
                addView(button(getString(R.string.database)) {
                    startActivity(Intent(this@MainActivity, DatabaseActivity::class.java))
                })
                addView(button(getString(R.string.recycler)) {
                    startActivity(Intent(this@MainActivity, RecyclerActivity::class.java))
                })
                addView(button(getString(R.string.start_port)) {
                    getRootProcess()?.run {
                        outputStream.bufferedWriter().use { output ->
                            output.write("setprop service.adb.tcp.port 10000\n")
                            output.flush()
                            output.write("stop adbd\n")
                            output.flush()
                            output.write("start adbd\n")
                            output.flush()
                            output.write("exit\n")
                            output.flush()
                        }
                        waitFor()
                    }
                })
                addView(button(getString(R.string.proxy)) {
                    startActivity(Intent(this@MainActivity, ProxyActivity::class.java))
                })
                addView(button(getString(R.string.compress)) {
                    startActivity(Intent(this@MainActivity, CompressActivity::class.java))
                })
            }
        )

        initObserver()
    }

    private fun getRootProcess(): Process? {
        return try {
            Runtime.getRuntime().exec("su")
        } catch (_: Exception) {
            null
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            onBroadcast(this@MainActivity, "EVENT_BROADCAST") {
                Toast.makeText(
                    this@MainActivity,
                    "Main接收到消息：${it.getStringExtra("test")}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        lifecycleScope.launch {
            FlowEventBus.onEvent<String>("EVENT") {
                Toast.makeText(this@MainActivity, "Main接收到消息：$it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}