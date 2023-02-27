package com.xaluoqone.practise.recycler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

//拖
open class ItemSortTouchHelper(
    private val drawOrientation: DrawOrientation = DrawOrientation.Unspecified
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = when (drawOrientation) {
            DrawOrientation.Horizontal -> ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            DrawOrientation.Vertical -> ItemTouchHelper.UP or ItemTouchHelper.DOWN
            DrawOrientation.Unspecified -> ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        recyclerView.parent.requestDisallowInterceptTouchEvent(true)
        val adapter = recyclerView.adapter
        // adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        // val adapter = recyclerView.adapter
        // adapter.onItemMoveEnd()
    }

    override fun isLongPressDragEnabled() = true
}

//删除
class ItemDeletableHelper(val onSwipe:(Int)->Unit): ItemTouchHelper.Callback(){
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0,  ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if(direction == ItemTouchHelper.LEFT){
            val position = viewHolder.adapterPosition
            onSwipe(position)
        }
    }
    override fun isLongPressDragEnabled() = false
}

//又拖又删除
class ItemSortDeletableTouchHelper(val onSwipe:(Int)->Unit,private val drawOrientation: DrawOrientation = DrawOrientation.Unspecified)
    :ItemSortTouchHelper(drawOrientation){

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = when (drawOrientation) {
            DrawOrientation.Horizontal -> ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            DrawOrientation.Vertical -> ItemTouchHelper.UP or ItemTouchHelper.DOWN
            DrawOrientation.Unspecified -> ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, ItemTouchHelper.LEFT)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if(direction == ItemTouchHelper.LEFT){
            val position = viewHolder.adapterPosition
            onSwipe(position)
        }
    }
}


sealed interface DrawOrientation {
    object Vertical : DrawOrientation
    object Horizontal : DrawOrientation
    object Unspecified : DrawOrientation
}

//删除
fun RecyclerView.applyItemDeletableTouchHelper(onSwipe:(Int)->Unit){
    ItemTouchHelper(ItemDeletableHelper(onSwipe)).also {
        it.attachToRecyclerView(this)
    }
}
//拖
fun RecyclerView.applyItemSortTouchHelper(drawOrientation: DrawOrientation = DrawOrientation.Unspecified) {
    ItemTouchHelper(ItemSortTouchHelper(drawOrientation)).also {
        it.attachToRecyclerView(this)
    }
}
//又拖又删除
fun RecyclerView.applyItemSortDeletableTouchHelper(onSwipe:(Int)->Unit, drawOrientation: DrawOrientation = DrawOrientation.Unspecified){
    ItemTouchHelper(ItemSortDeletableTouchHelper(onSwipe,drawOrientation)).also {
        it.attachToRecyclerView(this)
    }
}
