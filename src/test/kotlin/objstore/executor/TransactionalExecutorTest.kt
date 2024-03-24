package objstore.executor

import com.github.yundom.objstore.executor.TransactionalExecutor
import objstore.fake.FakeCommand
import objstore.fake.transaction.FakeTransactionBegin
import objstore.fake.transaction.FakeTransactionCommit
import objstore.fake.transaction.FakeTransactionRollback
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class TransactionalExecutorTest {

    private lateinit var executor: TransactionalExecutor

    private var messageReceived: String? = null

    @BeforeEach
    fun setUp() {
        messageReceived = null
        executor = TransactionalExecutor { message -> messageReceived = message }
    }

    @Test
    fun `Executing a SampleCommand should execute it`() {
        val command = FakeCommand()

        executor.execute(command)

        assertTrue(command.executed)
    }

    @Test
    fun `Rollback without a transaction should trigger a no transaction message`() {
        executor.execute(FakeTransactionRollback())

        assertTrue(messageReceived == "no transaction")
    }

    @Test
    fun `Rollback should undo commands until the last TransactionBegin`() {
        val command1 = FakeCommand()
        val command2 = FakeCommand()

        executor.execute(FakeTransactionBegin())
        executor.execute(command1)
        executor.execute(command2)
        executor.execute(FakeTransactionRollback())

        assertTrue(command1.executed)
        assertTrue(command2.executed)
        assertTrue(command1.undone)
        assertTrue(command2.undone)
    }

    @Test
    fun `Rollback and nested transactions should undo nested commands`() {
        val command1 = FakeCommand()
        val command2 = FakeCommand()
        val command3 = FakeCommand()

        executor.execute(FakeTransactionBegin())
        executor.execute(command1)
        executor.execute(command2)
        executor.execute(FakeTransactionBegin())
        executor.execute(command3)
        executor.execute(FakeTransactionRollback())
        executor.execute(FakeTransactionCommit())

        assertTrue(command1.executed)
        assertTrue(command2.executed)
        assertFalse(command1.undone)
        assertFalse(command2.undone)
        assertTrue(command3.executed)
        assertTrue(command3.undone)
    }

    @Test
    fun `Commit should not allow rollback`() {
        val command = FakeCommand()
        executor.execute(FakeTransactionBegin())
        executor.execute(command)
        executor.execute(FakeTransactionCommit())
        executor.execute(FakeTransactionRollback())

        assertTrue(command.executed)
        assertFalse(command.undone)
        assertTrue(messageReceived == "no transaction")
    }
}