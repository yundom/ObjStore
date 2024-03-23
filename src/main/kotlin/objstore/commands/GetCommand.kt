package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.core.ObjStore

class GetCommand(
    private val objStore: ObjStore<String, String>,
    private val param: Param
): BaseCommand() {

    override fun execute() {
        objStore.get(param.key).also {
            if (it != null) {
                param.onMessage(it)
            } else {
                param.onMessage("Key not set")
            }
        }
    }

    data class Param(val key: String, val onMessage: (String) -> Unit)
}