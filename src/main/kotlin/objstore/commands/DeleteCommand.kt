package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.core.ObjStore
import com.github.yundom.objstore.executor.Confirmable

class DeleteCommand(
    private val objStore: ObjStore<String, String>,
    private val param: Param,
): BaseCommand(), Confirmable {
    private var oldValue: String? = null

    override fun execute() {
        oldValue = objStore.remove(param.key)
    }

    override fun undo() {
        oldValue?.also { objStore.set(param.key, it) }
    }

    data class Param(val key: String)

    override fun prompt(): String {
        return "Are you sure?"
    }
}