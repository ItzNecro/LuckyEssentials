package dev.luckynetwork.id.lyrams.extensions

import org.bukkit.entity.Player

internal fun Player.checkPermission(permission: String, others: Boolean = false): Boolean {
    var toCheck = "luckyessentials." + permission.toLowerCase()

    if (others)
        toCheck = "luckyessentials." + permission.toLowerCase() + ".others"

    val boolean = this.hasPermission(toCheck)

    if (!boolean) {
        this.sendMessage("§e§lLuckyNetwork §a/ §cYou are lacking the permission $toCheck!")
    }

    return boolean
}

internal fun Player.checkPermissionSilent(permission: String, others: Boolean = false): Boolean {
    var toCheck = "luckyessentials." + permission.toLowerCase()

    if (others)
        toCheck = "luckyessentials." + permission.toLowerCase() + ".others"

    return hasPermission(toCheck)
}