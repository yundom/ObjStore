package objstore

import com.github.yundom.objstore.MemoryObjStore
import com.github.yundom.objstore.ObjStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemoryObjStoreTest {

    private lateinit var objStore: ObjStore<String, String>

    @BeforeEach
    fun setUp() {
        objStore = MemoryObjStore()
    }

    @Test
    fun `Set and get value`() {
        objStore.set("key", "value")
        assertEquals("value", objStore.get("key"))
    }

    @Test
    fun `Override value should update count correctly`() {
        objStore.set("key1", "value1")
        val oldValue = objStore.set("key1", "value2")

        assertEquals(0, objStore.count("value1"))
        assertEquals(1, objStore.count("value2"))
        assertEquals("value1", oldValue)
        assertEquals("value2", objStore.get("key1"))
    }

    @Test
    fun `Set should update value count correctly`() {
        objStore.set("key1", "value1")
        objStore.set("key2", "value1")
        assertEquals(2, objStore.count("value1"))

        objStore.set("key1", "value2")
        assertEquals(1, objStore.count("value1"))
        assertEquals(1, objStore.count("value2"))
    }

    @Test
    fun `Get should retrieve the correct value for a key`() {
        objStore.set("key1", "value1")
        assertEquals("value1", objStore.get("key1"))
        assertEquals(null, objStore.get("key2"))
    }

    @Test
    fun `Removing a value should decrease count`() {
        objStore.set("key1", "value1")
        objStore.set("key2", "value1")
        val oldCount = objStore.count("value1")
        val oldValue = objStore.remove("key1")
        val newCount = objStore.count("value1")


        assertEquals(2, oldCount)
        assertEquals("value1", oldValue)
        assertEquals(1, newCount)
    }

    @Test
    fun `Count should return the correct number of occurrences of a value`() {
        objStore.set("key1", "value1")
        objStore.set("key2", "value2")
        objStore.set("key3", "value1")

        assertEquals(2, objStore.count("value1"))
        assertEquals(1, objStore.count("value2"))
        assertEquals(0, objStore.count("value3"))
    }
}