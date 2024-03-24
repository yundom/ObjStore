package objstore.commands

import com.github.yundom.objstore.commands.DeleteCommand
import objstore.fake.FakeObjStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteCommandTest {

    private lateinit var objStore: FakeObjStore

    @BeforeEach
    fun setUp() {
        objStore = FakeObjStore()
    }

    @Test
    fun `execute should remove the key from the objStore`() {
        objStore.set("key", "value")
        val command = DeleteCommand(objStore, DeleteCommand.Param("key"))

        val before = objStore.get("key")
        command.execute()
        val after = objStore.get("key")

        assertEquals("value", before)
        assertNull(after)
    }

    @Test
    fun `undo should restore the removed key and value`() {
        objStore.set("key", "value")
        val command = DeleteCommand(objStore, DeleteCommand.Param("key"))

        command.execute()
        val before = objStore.get("key")
        command.undo()
        val after = objStore.get("key")

        assertNull(before)
        assertEquals("value", after)
    }
}