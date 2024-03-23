package objstore.commands

import com.github.yundom.objstore.commands.SetCommand
import objstore.fake.FakeObjStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetCommandTest {

    private lateinit var objStore: FakeObjStore

    @BeforeEach
    fun setUp() {
        objStore = FakeObjStore()
    }

    @Test
    fun `execute should set value in objStore`() {
        val command = SetCommand(objStore, SetCommand.Param("key", "value"))

        command.execute()

        assertEquals("value", objStore.get("key"))
    }

    @Test
    fun `Undo should set oldValue if it is not null`() {
        val command = SetCommand(objStore, SetCommand.Param("key", "value"))
        objStore.set("key", "oldValue")

        command.execute()
        val newValue = objStore.get("key")
        command.undo()
        val oldValue = objStore.get("key")

        assertEquals("value", newValue)
        assertEquals("oldValue", oldValue)
    }

    @Test
    fun `Undo should remove value if oldValue is null`() {
        val command = SetCommand(objStore, SetCommand.Param("key", "value"))

        command.execute()
        val newValue = objStore.get("key")
        command.undo()
        val oldValue = objStore.get("key")

        assertEquals("value", newValue)
        assertNull(oldValue)
    }

    @Test
    fun `Undo multiple commands should set value correctly`() {
        val command1 = SetCommand(objStore, SetCommand.Param("key", "value1"))
        val command2 = SetCommand(objStore, SetCommand.Param("key", "value2"))
        val command3 = SetCommand(objStore, SetCommand.Param("key", "value3"))

        command1.execute()
        command2.execute()
        command3.execute()
        val newValue = objStore.get("key")
        command3.undo()
        val oldValue3 = objStore.get("key")
        command2.undo()
        val oldValue2 = objStore.get("key")
        command1.undo()
        val oldValue1 = objStore.get("key")

        assertEquals("value3", newValue)
        assertEquals("value2", oldValue3)
        assertEquals("value1", oldValue2)
        assertNull(oldValue1)
    }
}