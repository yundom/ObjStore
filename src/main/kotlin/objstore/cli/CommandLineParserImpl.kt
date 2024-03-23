package com.github.yundom.objstore.cli

import com.github.yundom.command.Command
import com.github.yundom.objstore.cli.parser.CommandLineParser
import com.github.yundom.objstore.commands.factory.CommandFactory

class CommandLineParserImpl(
    private val factory: CommandFactory
): CommandLineParser {
    override fun parse(input: String): Command? {
        val args = input.split(" ")
        return if (args.isNotEmpty()) {
            factory.create(args)
        } else {
            null
        }
    }
}

