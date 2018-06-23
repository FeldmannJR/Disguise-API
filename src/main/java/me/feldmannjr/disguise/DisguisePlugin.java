package me.feldmannjr.disguise;

import me.feldmannjr.disguise.cmds.CmdDisguise;
import me.feldmannjr.disguise.listeners.BukkitListener;
import me.feldmannjr.disguise.listeners.PacketListener;
import me.feldmannjr.disguise.listeners.PlayerPacketListener;
import me.feldmannjr.disguise.versions.PluginVersion;
import me.feldmannjr.disguise.versions.v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.packetlistener.PacketListenerAPI;

public class DisguisePlugin extends JavaPlugin {

    public static PluginVersion nms = null;
    public static String skinUrl = null;

    @Override
    public void onEnable() {
        nms = new v1_8_R3();
        PacketListenerAPI.addPacketHandler(new PlayerPacketListener());
        PacketListenerAPI.addPacketHandler(new PacketListener());
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);
        Bukkit.getPluginCommand("disguise").setExecutor(new CmdDisguise());
        loadConfig();
    }

    @Override
    public void onDisable() {

    }

    public void loadConfig() {
        this.getDataFolder().mkdir();
        if (getConfig().get("skinurl") == null) {
            getConfig().set("skinurl", " https://sessionserver.mojang.com/session/minecraft/profile/");
        }
        saveConfig();
        skinUrl = getConfig().getString("skinurl");

    }

    public static Player getEntityById(int id) {
        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p.getEntityId() == id) {
                return p;
            }
        }

        return null;
    }
}
