package dev.luckynetwork.id.lyrams.commands.features.essentials

import dev.luckynetwork.id.lyrams.extensions.checkPermission
import dev.luckynetwork.id.lyrams.extensions.colorize
import dev.luckynetwork.id.lyrams.objects.Config
import dev.luckynetwork.id.lyrams.utils.BetterCommand
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EditSignCMD : BetterCommand("editsign") {

    override fun execute(
        sender: CommandSender,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (sender !is Player || !sender.checkPermission("editsign"))
            return false

        val nullSet: Set<Material>? = null
        val targetBlock = sender.getTargetBlock(nullSet, 5)

        if (targetBlock.state !is Sign) {
            sender.sendMessage(Config.prefix + " §cThat block is not a sign!")
            return false
        }

        val sign = targetBlock.state as Sign

        if (args.isEmpty()) {
            sendUsage(sender)
            return false
        }

        try {
            if (args[0].equals("set", true) && args.size > 2) {
                val line = args[1].toInt() - 1
                val argsAsString = args.joinToString(" ")
                val text = argsAsString.split(args[1] + " ")[1].colorize

                sign.setLine(line, text)
                sign.update()

                sender.sendMessage(Config.prefix + " §aLine " + args[1] + " updated!")
            } else if (args[0].equals("clear", true)) {
                if (args.size == 1) {
                    for (i in 0..3) {
                        sign.setLine(i, "")
                        sign.update()
                    }
                    sender.sendMessage(Config.prefix + " §aSign cleared!")

                } else {
                    val line = args[1].toInt() - 1
                    sign.setLine(line, "")
                    sign.update()

                    sender.sendMessage(Config.prefix + " §aLine " + args[1] + " cleared!")
                }

            } else {
                sendUsage(sender)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}

private fun sendUsage(sender: CommandSender) {
    sender.sendMessage("§cUsage: /editsign <line> <text>")
    sender.sendMessage("§cUsage: /editsign <clear> <line>")
    sender.sendMessage("§cUsage: /editsign clear")
}