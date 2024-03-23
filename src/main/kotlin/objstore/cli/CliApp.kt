package com.github.yundom.objstore.cli

import com.github.yundom.command.Executor
import com.github.yundom.objstore.cli.parser.CommandLineParser

class CliApp(
    private val executor: Executor,
    private val parser: CommandLineParser,
) {
    fun parse(input: String): Boolean {
        return parser.parse(input)?.also {
            executor.execute(it)
        } != null
    }
}