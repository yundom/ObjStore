package com.github.yundom.objstore.executor

import com.github.yundom.command.Command
import com.github.yundom.command.Executor
import com.github.yundom.objstore.executor.transaction.TransactionBegin
import com.github.yundom.objstore.executor.transaction.TransactionCommit
import com.github.yundom.objstore.executor.transaction.TransactionRollback

class TransactionalExecutor(private val onMessage: (String) -> Unit = {}): Executor {

    private var transactionStackCount: Int = 0

    private var history: MutableList<Command> = mutableListOf()

    override fun execute(command: Command, onPrompt: (String) -> Boolean) {
        when (command) {
            is TransactionBegin -> {
                transactionStackCount++
                history.add(command)
            }
            is TransactionRollback -> {
                if (transactionStackCount > 0) {
                    command.exec(onPrompt) {
                        while (history.isNotEmpty()) {
                            val prevCmd = history.removeLast()
                            prevCmd.undo()
                            if (prevCmd is TransactionBegin) {
                                transactionStackCount--
                                break
                            }
                        }
                    }
                } else {
                    onMessage("no transaction")
                }
            }
            is TransactionCommit -> {
                if (transactionStackCount > 0) {
                    command.exec(onPrompt) {
                        history.add(command)
                        transactionStackCount--
                    }
                } else {
                    onMessage("no transaction")
                }
            }
            else -> {
                command.exec(onPrompt) {
                    history.add(command)
                }
            }
        }
    }
}

private fun Command.exec(onPrompt: (String) -> Boolean, onExecuted: () -> Unit) {
    if (this is Confirmable) {
        onPrompt(prompt()).also { confirm ->
            if (confirm) {
                execute()
                onExecuted()
            }
        }
    } else {
        execute()
        onExecuted()
    }
}