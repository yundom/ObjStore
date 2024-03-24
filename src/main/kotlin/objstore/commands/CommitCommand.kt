package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.executor.Confirmable
import com.github.yundom.objstore.executor.transaction.TransactionCommit

class CommitCommand: BaseCommand(), TransactionCommit, Confirmable {
    override fun execute() {
        // No-op
    }

    override fun prompt(): String {
        return "Are you sure?"
    }
}