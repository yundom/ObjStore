package com.github.yundom.objstore

class MemoryObjStore<K, V>: ObjStore<K, V> {

    private val keyValueMap: MutableMap<K, V> = mutableMapOf()

    private val valueCountMap: MutableMap<V, Int> = mutableMapOf()

    override fun set(key: K, value: V): V? {
        val oldValue = keyValueMap.put(key, value)
        oldValue?.decreaseCountForValue(valueCountMap)
        value.increaseCountForValue(valueCountMap)
        return oldValue
    }

    override fun get(key: K): V? = keyValueMap[key]

    override fun count(value: V): Int = valueCountMap.getOrDefault(value, 0)

    override fun remove(key: K): V? {
        keyValueMap[key]?.decreaseCountForValue(valueCountMap)
        return keyValueMap.remove(key)
    }

    private fun V.increaseCountForValue(map: MutableMap<V, Int>) {
        map[this] = map.getOrDefault(this, 0) + 1
    }

    private fun V.decreaseCountForValue(map: MutableMap<V, Int>) {
        val count = map.getOrDefault(this, 0) - 1
        if (count <= 0) {
            map.remove(this)
        } else {
            map[this] = count
        }
    }
}