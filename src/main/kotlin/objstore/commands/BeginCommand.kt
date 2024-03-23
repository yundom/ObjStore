package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.executor.transaction.TransactionBegin

class BeginCommand: BaseCommand(), TransactionBegin {
    override fun execute() {
        // No-op
    }
}