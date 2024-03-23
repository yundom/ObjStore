package com.github.yundom.objstore.commands

import com.github.yundom.command.BaseCommand
import com.github.yundom.objstore.executor.transaction.TransactionRollback

class RollbackCommand: BaseCommand(), TransactionRollback {
    override fun execute() {
        // No-op
    }
}