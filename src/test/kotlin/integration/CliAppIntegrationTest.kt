package integration

import com.github.yundom.command.Executor
import com.github.yundom.objstore.cli.Cli
import com.github.yundom.objstore.cli.CliApp
import com.github.yundom.objstore.cli.CommandLineParserImpl
import com.github.yundom.objstore.cli.parser.CommandLineParser
import com.github.yundom.objstore.commands.factory.CommandFactory
import com.github.yundom.objstore.commands.factory.CommandFactoryImpl
import com.github.yundom.objstore.core.MemoryObjStore
import com.github.yundom.objstore.executor.TransactionalExecutor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CliAppIntegrationTest {

    private lateinit var app: CliApp

    private val messages: MutableList<String> = mutableListOf()

    @BeforeEach
    fun setUp() {
        val output: Cli = { messages.add(it) }
        val objStore = MemoryObjStore<String, String>()
        val factory: CommandFactory = CommandFactoryImpl(objStore, output)
        val parser: CommandLineParser = CommandLineParserImpl(factory)
        val executor: Executor = TransactionalExecutor(output)
        app = CliApp(executor, parser)
    }

    @Test
    fun `Scenario - set and get a value`() {
        val inputs = listOf(
            "SET foo 123",
            "GET foo"
        )

        inputs.forEach { app.parse(it) { true } }

        assertEquals(listOf(
            "123"
        ), messages)
    }

    @Test
    fun `Scenario - delete a value`() {
        val inputs = listOf(
            "DELETE foo",
            "GET foo"
        )

        inputs.forEach { app.parse(it) { true } }

        assertEquals(listOf(
            "key not set"
        ), messages)
    }

    @Test
    fun `Scenario - count the number of occurrences of a value`() {
        val inputs = listOf(
            "SET foo 123",
            "SET bar 456",
            "SET baz 123",
            "COUNT 123",
            "COUNT 456"
        )

        inputs.forEach { app.parse(it) { true } }

        assertEquals(listOf(
            "2",
            "1"
        ), messages)
    }

    @Test
    fun `Scenario - commit a transaction`() {
        val inputs = listOf(
            "SET bar 123",
            "GET bar",
            "BEGIN",
            "SET foo 456",
            "GET bar",
            "DELETE bar",
            "COMMIT",
            "GET bar",
            "ROLLBACK",
            "GET foo"
        )

        inputs.forEach { app.parse(it) { true } }

        assertEquals(listOf(
            "123",
            "123",
            "key not set",
            "no transaction",
            "456"
        ), messages)
    }

    @Test
    fun `Scenario - rollback a transaction`() {
        val inputs = listOf(
            "SET foo 123",
            "SET bar abc",
            "BEGIN",
            "SET foo 456",
            "GET foo",
            "SET bar def",
            "GET bar",
            "ROLLBACK",
            "GET foo",
            "GET bar",
            "COMMIT"
        )

        inputs.forEach { app.parse(it) { true } }

        assertEquals(listOf(
            "456",
            "def",
            "123",
            "abc",
            "no transaction"
        ), messages)
    }

    @Test
    fun `Scenario - nested transactions`() {
        val inputs = listOf(
            "SET foo 123",
            "SET bar 456",
            "BEGIN",
            "SET foo 456",
            "BEGIN",
            "COUNT 456",
            "GET foo",
            "SET foo 789",
            "GET foo",
            "ROLLBACK",
            "GET foo",
            "DELETE foo",
            "GET foo",
            "ROLLBACK",
            "GET foo"
        )

        inputs.forEach { app.parse(it) { true } }

        assertEquals(listOf(
            "2",
            "456",
            "789",
            "456",
            "key not set",
            "123"
        ), messages)
    }
}