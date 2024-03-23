package com.github.yundom.objstore.commands.factory

import com.github.yundom.command.Command
import com.github.yundom.objstore.core.MemoryObjStore
import com.github.yundom.objstore.commands.*

class CommandFactoryImpl(
    private val objStore: MemoryObjStore<String, String>,
    private val onMessage: (String) -> Unit = {},
): CommandFactory {
    override fun create(params: List<String>): Command? {
        val cmdName = params.getOrNull(0)?.toLowerCase()
        return when (cmdName) {
            "set" -> {
                if (params.size == 3) {
                    createSetCommand(params[1], params[2])
                } else {
                    null
                }
            }
            "get" -> {
                if (params.size == 2) {
                    createGetCommand(params[1])
                } else {
                    null
                }
            }
            "delete" -> {
                if (params.size == 2) {
                    createDeleteCommand(params[1])
                } else {
                    null
                }
            }
            "count" -> {
                if (params.size == 2) {
                    createCountCommand(params[1])
                } else {
                    null
                }
            }
            "begin" -> createBeginCommand()
            "commit" -> createCommitCommand()
            "rollback" -> createRollbackCommand()
            else -> null
        }
    }

    private fun createSetCommand(key: String, value: String): Command {
        return SetCommand(objStore, SetCommand.Param(key, value))
    }

    private fun createGetCommand(key: String): Command {
        return GetCommand(objStore, GetCommand.Param(key, onMessage))
    }

    private fun createDeleteCommand(key: String): Command {
        return DeleteCommand(objStore, DeleteCommand.Param(key))
    }

    private fun createCountCommand(value: String): Command {
        return CountCommand(objStore, CountCommand.Param(value, onMessage))
    }

    private fun createBeginCommand(): Command {
        return BeginCommand()
    }

    private fun createCommitCommand(): Command {
        return CommitCommand()
    }

    private fun createRollbackCommand(): Command {
        return RollbackCommand()
    }
}