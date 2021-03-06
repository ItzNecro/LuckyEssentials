package dev.luckynetwork.id.lyrams.commands.features.pluginmanager

import dev.luckynetwork.id.lyrams.extensions.checkPermission
import dev.luckynetwork.id.lyrams.extensions.disable
import dev.luckynetwork.id.lyrams.objects.Config
import dev.luckynetwork.id.lyrams.objects.PluginUtils
import dev.luckynetwork.id.lyrams.utils.SubCommand
import org.bukkit.command.CommandSender

class DisableCMD(name: String, vararg aliases: String) : SubCommand(name, *aliases) {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (!sender.checkPermission("pluginmanager.disable"))
            return
        if (args.isEmpty())
            return sender.sendMessage(Config.prefix + " §cPlease provide a plugin!")

        val plugin =
            PluginUtils.getPluginByName(args[0]) ?: return sender.sendMessage(Config.prefix + " §cPlugin not found!")
        if (!plugin.isEnabled) {
            sender.sendMessage(Config.prefix + " §cPlugin is already disabled!")
            return
        }
        plugin.disable()
        sender.sendMessage(Config.prefix + " §a§l${args[0]} §adisabled!")
    }

}