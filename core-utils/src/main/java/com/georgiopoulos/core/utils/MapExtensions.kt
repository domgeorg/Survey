package com.georgiopoulos.core.utils

fun <K, V> MutableMap<K, V>.replaceAndReturn(key: K, value: V): MutableMap<K, V> {
    replace(key, value)
    return this
}