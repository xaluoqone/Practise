package com.xaluoqone.practise.recycler

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xaluoqone.practise.ex.configSystemBar
import com.xaluoqone.practise.ex.dp
import com.xaluoqone.practise.widget.linear
import kotlin.math.roundToInt

class RecyclerActivity : AppCompatActivity() {

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
        return linear {
            orientation = LinearLayoutCompat.VERTICAL
            addView(list {
                layoutManager = layoutManager()
                adapter = DeviceListAdapter(
                    listOf(
                        ListItem.Room("R1"),
                        ListItem.Device("R1-D1"),
                        ListItem.Device("R1-D2"),
                        ListItem.Device("R1-D3"),
                        ListItem.Device("R1-D4"),
                        ListItem.Device("R1-D5"),
                        ListItem.Device("R1-D6"),
                        ListItem.Room("R2"),
                        ListItem.Device("R2-D1"),
                        ListItem.Device("R2-D2"),
                        ListItem.Device("R2-D3"),
                        ListItem.Device("R2-D4"),
                        ListItem.Room("R3"),
                        ListItem.Device("R3-D1"),
                        ListItem.Device("R3-D2"),
                        ListItem.Device("R3-D3"),
                        ListItem.Device("R3-D4"),
                        ListItem.Room("R4"),
                        ListItem.Device("R4-D1"),
                        ListItem.Device("R4-D2"),
                        ListItem.Device("R4-D3"),
                        ListItem.Device("R4-D4"),
                        ListItem.Device("R5-D4"),
                    )
                )
                applyItemSortTouchHelper(DrawOrientation.Unspecified)
            })
        }
    }

    context (ViewGroup)
    private fun list(block: RecyclerView.() -> Unit): RecyclerView {
        return RecyclerView(context).apply(block)
    }

    context (RecyclerView)
    private fun layoutManager(): GridLayoutManager {
        return GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when((adapter as DeviceListAdapter).listData[position]){
                        is ListItem.Device -> 1
                        is ListItem.Room -> 2
                    }
                }
            }
        }
    }

    sealed interface ListItem {
        data class Room(val id: String) : ListItem

        data class Device(val id: String) : ListItem
    }

    class DeviceListAdapter(
        val listData: List<ListItem>
    ) : RecyclerView.Adapter<DeviceListAdapter.Holder>() {

        class Holder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return when (viewType) {
                1 -> Holder(
                    TextView(parent.context).apply {
                        layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, 40.dp.roundToInt())
                        text = "Room"
                        setBackgroundColor("#00ff00".toColorInt())
                    }
                )
                else -> Holder(
                    TextView(parent.context).apply {
                        layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, 120.dp.roundToInt())
                        text = "Device"
                        setBackgroundColor("#ff0000".toColorInt())
                    }
                )
            }
        }

        override fun getItemCount(): Int {
            return listData.size
        }

        override fun getItemViewType(position: Int): Int {
            return when (listData[position]) {
                is ListItem.Device -> 2
                is ListItem.Room -> 1
            }
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {

        }
    }
}