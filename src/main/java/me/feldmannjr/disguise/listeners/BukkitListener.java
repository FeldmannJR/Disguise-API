package me.feldmannjr.disguise.listeners;

import me.feldmannjr.disguise.DisguiseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        DisguiseAPI.removeDisguise(ev.getPlayer().getUniqueId());
    }
}
