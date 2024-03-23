package com.github.yundom.objstore.commands.factory

import com.github.yundom.command.Command

interface CommandFactory {
    fun create(params: List<String>): Command?
}