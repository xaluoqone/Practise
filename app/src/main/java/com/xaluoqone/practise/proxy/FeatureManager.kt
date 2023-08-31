package com.xaluoqone.practise.proxy

import com.xaluoqone.practise.ktx.logv
import java.lang.reflect.Proxy

class FeatureImpl private constructor() : IFeatures {

    companion object {
        private val feature by lazy {
            FeatureImpl()
        }

        fun create(): IFeatures {
            val clazz = IFeatures::class.java
            return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz)) { _, method, args ->
                logv("执行${method.name}前 ---")
                val res = if (args == null) method.invoke(feature) else method.invoke(feature, *args)
                logv("执行${method.name}完成 ---")
                res
            } as IFeatures
        }
    }

    override fun fn1(p: String) {
        logv("正在执行fn1..., params: p=$p")
    }

    override fun fn2() {
        logv("正在执行fn2...")
    }

    override fun fn3() {
        logv("正在执行fn3...")
    }

    override fun fn4() {
        logv("正在执行fn4...")
    }
}