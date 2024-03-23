package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.core.ObjStore

class SetCommand(
    private val objStore: ObjStore<String, String>,
    private val param: Param,
): BaseCommand() {
    private var oldValue: String? = null

    override fun execute() {
        oldValue = objStore.set(param.key, param.value)
    }

    override fun undo() {
        if (oldValue == null) {
            objStore.remove(param.key)
        } else {
            oldValue?.also { objStore.set(param.key, it) }
        }
    }

    data class Param(val key: String, val value: String)
}