package com.github.yundom.objstore.cli.parser

import com.github.yundom.command.Command

interface CommandLineParser {
    fun parse(input: String): Command?
}