package com.xaluoqone.practise.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import com.xaluoqone.practise.R
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.dpi
import com.xaluoqone.practise.widget.button
import com.xaluoqone.practise.widget.layoutParams
import com.xaluoqone.practise.widget.linear
import com.xaluoqone.practise.widget.match
import com.xaluoqone.practise.widget.vertical
import kotlinx.coroutines.launch

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = contentView()
        configSystemBar {
            statusBarBlackFont = true
            applyStatusBarView = contentView
        }
        setContentView(contentView)
    }

    private fun contentView() = linear {
        orientation = vertical

        addView(button(getString(R.string.send_flow_event)) {
            FlowEventBus.post("EVENT", "这是一个普通的FlowEvent")
        })
        addView(button(getString(R.string.send_broadcast_flow_event)) {
            broadcast("EVENT_BROADCAST") {
                putExtra("test", "这是一个广播的FlowEvent")
            }
        })
        addView(button(getString(R.string.send_flow_event2)) {
            lifecycleScope.launch {
                FlowEventBus2.post("FLOW_EVENT2", "这是一个FlowEvent2发送的普通Event")
            }
        })
        addView(button(getString(R.string.send_broadcast_flow_event2)) {
            FlowEventBus2.postBroadcast("FLOW_EVENT_BROADCAST2") {
                putString("test", "这是一个FlowEvent2发送的广播Event")
            }
        })
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