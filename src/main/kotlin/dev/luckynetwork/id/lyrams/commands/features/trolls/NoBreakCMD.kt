package dev.luckynetwork.id.lyrams.commands.features.trolls

import dev.luckynetwork.id.lyrams.extensions.applyMetadata
import dev.luckynetwork.id.lyrams.extensions.checkPermission
import dev.luckynetwork.id.lyrams.extensions.removeMetadata
import dev.luckynetwork.id.lyrams.objects.Config
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class NoBreakCMD : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, cmd: String?, args: Array<out String>?): Boolean {

        if (!Config.trollEnabled)
            return false

        if (!sender!!.checkPermission("troll.nobreak"))
            return false

        var target: Player

        target =
                // if console executes this
            if (sender !is Player) {
                // console must specify a player
                if (args!!.isEmpty()) {
                    sender.sendMessage(Config.prefix + " §cInvalid usage!")
                    return false
                }

                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage(Config.prefix + " §cPlayer not found!")
                    return false
                }

                Bukkit.getPlayer(args[0])

                // if executed by player
            } else
                sender

        var others = false

        if (args!!.isNotEmpty() && sender is Player) {
            if (Bukkit.getPlayer(args[0]) == null) {
                target.sendMessage(Config.prefix + " §cPlayer not found!")
                return false
            }

            target = Bukkit.getPlayer(args[0])
            others = true

        }

        if (!sender.checkPermission("troll.nobreak", others))
            return false

        val state =
            if (target.hasMetadata("NOBREAK")) {
                target.removeMetadata("NOBREAK")
                true
            } else {
                target.applyMetadata("NOBREAK", true)
                false
            }


        when {

            others ->
                if (state)
                    sender.sendMessage(Config.prefix + " §aEnabled block-breaking for §l" + target.name + "!")
                else
                    sender.sendMessage(Config.prefix + " §cDisabled block-breaking for §l" + target.name + "!")
            else ->
                if (state)
                    target.sendMessage(Config.prefix + " §aYou can now break blocks!")
                else
                    target.sendMessage(Config.prefix + " §cYou can no longer break blocks!")

        }

        return false

    }

}