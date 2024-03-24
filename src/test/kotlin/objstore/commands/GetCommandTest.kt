package objstore.commands

import com.github.yundom.objstore.commands.GetCommand
import objstore.fake.FakeObjStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCommandTest {

    private lateinit var objStore: FakeObjStore

    @BeforeEach
    fun setUp() {
        objStore = FakeObjStore()
    }

    @Test
    fun `execute should call onMessage with value when key exists`() {
        objStore.set("key", "value")
        var message: String? = null
        val command = GetCommand(objStore, GetCommand.Param("key") { message = it })

        command.execute()

        assertEquals("value", message)
    }

    @Test
    fun `execute should call onMessage with 'Key not set' when key does not exist`() {
        var message: String? = null
        val command = GetCommand(objStore, GetCommand.Param("key") { message = it })

        command.execute()

        assertEquals("key not set", message)
    }
}