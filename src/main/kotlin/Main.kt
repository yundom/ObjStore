package com.github.yundom

fun main() {
    val output = Components.provideCli()
    val app = Components.provideCliApp()

    while (true) {
        print("> ")

        val input = readlnOrNull() ?: continue

        if (input.equals("exit", ignoreCase = true)) {
            break
        }

        if (app.parse(input).not()) {
            output("Unknown command: $input")
        }
    }
}