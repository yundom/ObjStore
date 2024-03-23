package com.github.yundom

import com.github.yundom.command.Executor
import com.github.yundom.objstore.cli.Cli
import com.github.yundom.objstore.cli.CliApp
import com.github.yundom.objstore.cli.CommandLineParserImpl
import com.github.yundom.objstore.cli.parser.CommandLineParser
import com.github.yundom.objstore.commands.factory.CommandFactory
import com.github.yundom.objstore.commands.factory.CommandFactoryImpl
import com.github.yundom.objstore.core.MemoryObjStore
import com.github.yundom.objstore.executor.TransactionalExecutor

object Components {
    fun provideCli(): Cli = { println(it) }

    fun provideCliApp(): CliApp {
        val output: Cli = provideCli()
        val objStore = MemoryObjStore<String, String>()
        val factory: CommandFactory = CommandFactoryImpl(objStore, output)
        val parser: CommandLineParser = CommandLineParserImpl(factory)
        val executor: Executor = TransactionalExecutor(output)
        return CliApp(executor, parser)
    }
}