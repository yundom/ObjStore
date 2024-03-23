package objstore.fake

import com.github.yundom.objstore.ObjStore

class FakeObjStore: ObjStore<String, String> {
    private val store = mutableMapOf<String, String>()

    override fun set(key: String, value: String): String? {
        return store.put(key, value)
    }

    override fun get(key: String): String? {
        return store[key]
    }

    override fun count(value: String): Int {
        return store.filterValues { it == value }.count()
    }

    override fun remove(key: String): String? {
        return store.remove(key)
    }
}