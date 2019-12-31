package me.syari.jp_chat

import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(ChatEvent, this)
    }
}