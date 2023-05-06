package me.scriptydude.deathtimeout.handlers

import me.scriptydude.deathtimeout.DeathTimeout
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.util.*

class PlayerHandler(private val plugin: DeathTimeout) : Listener {
    var endList : MutableList<Player> = mutableListOf()
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val plr = event.player
        val cfgPlrKill = plugin.config.getBoolean("ban-if-player")

        if (cfgPlrKill){
            if (plr.killer != null){
                endList.add(plr)
            }
        }else{
            endList.add(plr)
        }
    }
    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) = plrListCheck(event.player)
    @EventHandler
    fun onPlayerDisconnect(event: PlayerQuitEvent) = plrListCheck(event.player)
    private fun plrListCheck(plr: Player){
        val check = endList.find { it.name == plr.name }
        if (check != null){
            endList.remove(plr)
            banPlayer(plr)
        }
    }
    private fun banPlayer(plr: Player){
        val dateNow = Date()
        val banTime = plugin.config.getInt("ban-time")

        var dateBan : Date? = null
        if (banTime >= 1){
            dateBan = Date(dateNow.time + (banTime * 60000)) //minute in millis
        }

        val reason = plugin.config.getString("ban-message")

        plr.banPlayer(reason,dateBan, "DeathTimeout", true)
    }
}