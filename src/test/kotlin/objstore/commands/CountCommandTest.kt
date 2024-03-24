package objstore.commands

import com.github.yundom.objstore.commands.CountCommand
import objstore.fake.FakeObjStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CountCommandTest {

    private lateinit var objStore: FakeObjStore

    @BeforeEach
    fun setUp() {
        objStore = FakeObjStore()
    }

    @Test
    fun `execute should call onMessage with count of the value when value exists`() {
        objStore.set("key1", "value1")
        objStore.set("key2", "value1")
        var message: String? = null
        val command = CountCommand(objStore, CountCommand.Param("value1") { message = it })

        command.execute()

        assertEquals("2", message)
    }

    @Test
    fun `execute should call onMessage with '0' when value does not exist`() {
        var message: String? = null
        val command = CountCommand(objStore, CountCommand.Param("value1") { message = it })

        command.execute()

        assertEquals("0", message)
    }
}