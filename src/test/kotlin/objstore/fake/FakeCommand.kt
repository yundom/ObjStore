package objstore.fake

import com.github.yundom.command.Command

open class FakeCommand: Command {

    var executed = false

    var undone = false

    override fun execute() {
        executed = true
    }

    override fun undo() {
        undone = true
    }
}