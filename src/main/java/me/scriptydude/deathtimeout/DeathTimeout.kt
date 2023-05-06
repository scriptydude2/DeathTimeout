package me.scriptydude.deathtimeout

import me.scriptydude.deathtimeout.handlers.PlayerHandler
import org.bukkit.plugin.java.JavaPlugin
class DeathTimeout : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        if (config.getBoolean("enabled")){
            PlayerHandler(this)
        }
    }
    override fun onDisable() {
        // Plugin shutdown logic
    }
}