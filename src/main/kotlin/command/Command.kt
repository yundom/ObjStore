package com.github.yundom.command

interface Command {

    fun execute()

    fun undo()
}