package me.feldmannjr.disguise;

import me.feldmannjr.disguise.cmds.CmdDisguise;
import me.feldmannjr.disguise.listeners.PacketListener;
import me.feldmannjr.disguise.listeners.PlayerPacketListener;
import me.feldmannjr.disguise.v18.v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import java.util.ArrayList;

public class DisguisePlugin extends JavaPlugin {

    public static PluginVersion nms = null;

    public static String skinUrl = null;

    @Override
    public void onEnable() {
        nms = new v1_8_R3();
        PacketListenerAPI.addPacketHandler(new PlayerPacketListener());
        PacketListenerAPI.addPacketHandler(new PacketListener());
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

    public static Entity getEntityById(World w, int id) {
        for (Entity e: new ArrayList<Entity>(w.getEntities())) {
            if (e.getEntityId() == id) {
                return e;
            }
        }
        return null;
    }
}
