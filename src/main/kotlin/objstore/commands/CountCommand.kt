package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.core.ObjStore

class CountCommand(
    private val objStore: ObjStore<String, String>,
    private val param: Param
): BaseCommand() {

    override fun execute() {
        objStore.count(param.value).also {
            param.onMessage(it.toString())
        }
    }

    data class Param(val value: String, val onMessage: (String) -> Unit)
}