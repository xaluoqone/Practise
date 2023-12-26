package com.xaluoqone.practise.event

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.ArrayMap
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow

class FlowEventBus2 private constructor(context: Context) {
    val flowEvents = FlowEvents()
    val broadcastEvents = BroadcastEvents(context)

    companion object {
        private var instance: FlowEventBus2? = null

        fun init(context: Context) {
            instance = FlowEventBus2(context.applicationContext)
        }

        suspend fun post(key: String, event: Any) {
            instance?.run {
                flowEvents.set(key, event)
            }
        }

        suspend fun <T : Any> onEvent(key: String, block: suspend (T) -> Unit) {
            instance?.run {
                flowEvents.get<T>(key).collect {
                    block(it)
                }
            }
        }

        fun postBroadcast(key: String, block: Bundle.() -> Unit) {
            instance?.run {
                broadcastEvents[key] = Bundle().apply(block)
            }
        }

        suspend fun onBroadcast(key: String, block: suspend (Intent?) -> Unit) {
            instance?.run {
                broadcastEvents.get(key)?.collect {
                    block(it)
                }
            }
        }
    }
}

interface Events

class FlowEvents : Events {
    private val events = ArrayMap<String, MutableSharedFlow<Any>>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String): Flow<T> {
        if (events[key] == null) {
            events[key] = MutableSharedFlow()
        }
        return events[key] as Flow<T>
    }

    suspend fun <T : Any> set(key: String, value: T) {
        events[key]?.emit(value)
    }
}

class BroadcastEvents(private val context: Context) : Events {
    private val events = ArrayMap<String, Flow<Intent?>>()

    fun get(key: String): Flow<Intent?>? {
        if (events[key] == null) {
            events[key] = context.broadcastFlow(key)
        }
        return events[key]
    }

    operator fun set(key: String, bundle: Bundle) {
        context.sendBroadcast(Intent(key).apply {
            putExtras(bundle)
        })
    }
}

fun Context.broadcastFlow(key: String) = callbackFlow {
    val broadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            trySend(intent)
        }
    }

    registerReceiver(broadcast, IntentFilter(key))

    awaitClose {
        unregisterReceiver(broadcast)
    }
}