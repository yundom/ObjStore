package com.github.yundom.objstore

interface ObjStore<K, V> {
    /**
     * Associate the specified value with the specific key in the ObjStore
     * @return the previous value associated with the key, or null if the key was not present in the ObjStore.
     */
    fun set(key: K, value: V): V?

    /**
     * @return the value corresponding to the given key, or null if such a key is not present in the ObjStore.
     */
    fun get(key: K): V?

    /**
     * @return the number of the value in the ObjStore
     */
    fun count(value: V): Int

    /**
     * Removes the specified key and its corresponding value from this ObjStore.
     * @return the previous value associated with the key, or null if the key was not present in the ObjStore.
     */
    fun remove(key: K): V?
}