package com.xaluoqone.practise.event

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

object FlowEventBus {
    private val events = mutableMapOf<String, MutableSharedFlow<Any>>()
    private val broadcastReceivers = mutableMapOf<String, LifeCycleBroadcastReceiver>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun post(key: String, event: Any) {
        coroutineScope.launch {
            if (events[key] == null) {
                events[key] = MutableSharedFlow()
            }
            events[key]?.emit(event)
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> onEvent(key: String, block: suspend (T) -> Unit) {
        if (events[key] == null) {
            events[key] = MutableSharedFlow()
        }
        events[key]?.collect {
            block(it as T)
        }
    }

    fun broadcast(context: Context, key: String, block: Intent.() -> Unit) {
        val intent = Intent(key)
        intent.block()
        context.sendBroadcast(intent)
    }

    suspend fun onBroadcast(
        context: Context,
        owner: LifecycleOwner,
        key: String,
        block: suspend (Intent) -> Unit
    ) {
        if (broadcastReceivers[key] == null) {
            val receiver = LifeCycleBroadcastReceiver(context, key)
            owner.lifecycle.addObserver(receiver)
            broadcastReceivers[key] = receiver
        }
        broadcastReceivers[key]?.event?.collect {
            block(it)
        }
    }

    private class LifeCycleBroadcastReceiver(
        private val context: Context,
        private val key: String
    ) : DefaultLifecycleObserver {
        private val _event = MutableSharedFlow<Intent>()
        val event: SharedFlow<Intent> = _event
        private val coroutineScope = CoroutineScope(Dispatchers.Main)

        private val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                coroutineScope.launch {
                    _event.emit(intent)
                }
            }
        }

        override fun onCreate(owner: LifecycleOwner) {
            context.registerReceiver(broadcast, IntentFilter(key))
        }

        override fun onDestroy(owner: LifecycleOwner) {
            context.unregisterReceiver(broadcast)
        }
    }
}

fun Context.broadcast(key: String, block: Intent.() -> Unit) {
    FlowEventBus.broadcast(this, key, block)
}

suspend fun Context.onBroadcast(
    owner: LifecycleOwner,
    key: String,
    block: suspend (Intent) -> Unit
) {
    FlowEventBus.onBroadcast(this, owner, key, block)
}