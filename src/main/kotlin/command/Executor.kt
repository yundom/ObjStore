package com.github.yundom.command

interface Executor {
    fun execute(command: Command)
}