package objstore.commands.factory

import com.github.yundom.objstore.commands.CountCommand
import com.github.yundom.objstore.commands.DeleteCommand
import com.github.yundom.objstore.commands.GetCommand
import com.github.yundom.objstore.commands.SetCommand
import com.github.yundom.objstore.commands.factory.CommandFactory
import com.github.yundom.objstore.commands.factory.CommandFactoryImpl
import com.github.yundom.objstore.core.ObjStore
import objstore.fake.FakeObjStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CommandFactoryImplTest {
    private lateinit var commandFactory: CommandFactory
    private lateinit var objStore: ObjStore<String, String>

    @BeforeEach
    fun setUp() {
        objStore = FakeObjStore()
        commandFactory = CommandFactoryImpl(objStore) { message ->
            println(message)
        }
    }

    @Test
    fun `Create returns SetCommand with correct params`() {
        val command = commandFactory.create(listOf("set", "key1", "value1"))
        assertTrue(command is SetCommand)
    }

    @Test
    fun `Create returns null for invalid SetCommand params`() {
        val command = commandFactory.create(listOf("set", "key1"))
        assertNull(command)
    }

    @Test
    fun `Create returns GetCommand with correct params`() {
        val command = commandFactory.create(listOf("get", "key1"))
        assertTrue(command is GetCommand)
    }

    @Test
    fun `Create returns null for unknown command`() {
        val command = commandFactory.create(listOf("unknown"))
        assertNull(command)
    }

    @Test
    fun `Create returns DeleteCommand with correct params`() {
        val command = commandFactory.create(listOf("delete", "key1"))
        assertTrue(command is DeleteCommand)
    }

    @Test
    fun `Create returns null for invalid DeleteCommand params`() {
        val command = commandFactory.create(listOf("delete"))
        assertNull(command)
    }

    @Test
    fun `Create returns CountCommand with correct params`() {
        val command = commandFactory.create(listOf("count", "value1"))
        assertTrue(command is CountCommand)
    }

    @Test
    fun `Create returns null for invalid CountCommand params`() {
        val command = commandFactory.create(listOf("count"))
        assertNull(command)
    }
}