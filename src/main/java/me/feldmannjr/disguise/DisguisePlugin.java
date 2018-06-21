package me.feldmannjr.disguise;

import me.feldmannjr.disguise.cmds.CmdDisguise;
import me.feldmannjr.disguise.listeners.PacketListener;
import me.feldmannjr.disguise.v18.v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.packetlistener.PacketListenerAPI;

public class DisguisePlugin extends JavaPlugin {

    public static PluginVersion nms = null;

    @Override
    public void onEnable()
    {
        nms = new v1_8_R3();
        PacketListenerAPI.addPacketHandler(new PacketListener());
        Bukkit.getPluginCommand("disguise").setExecutor(new CmdDisguise());

    }

    @Override
    public void onDisable()
    {

    }


}
