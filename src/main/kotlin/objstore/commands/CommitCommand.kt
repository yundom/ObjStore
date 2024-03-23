package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.executor.transaction.TransactionCommit

class CommitCommand: BaseCommand(), TransactionCommit {
    override fun execute() {
        // No-op
    }
}