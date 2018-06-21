package me.feldmannjr.disguise;


import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class DisguiseData {

    UUID uid;
    int playerId;
    EntityType type;
    Player player;


    public DisguiseData(Player p, EntityType type)
    {
        this.player = p;
        this.uid = p.getUniqueId();
        this.playerId = p.getEntityId();
        this.type = type;

    }

    FieldResolver spawnResolver = null;
    NMSClassResolver nmsResolver = null;

    public void sendSpawn(Player p)
    {
        if (nmsResolver == null) {
            nmsResolver = new NMSClassResolver();
        }
        if (spawnResolver == null) {
            spawnResolver = new FieldResolver(nmsResolver.resolveSilent("PacketPlayOutSpawnEntityLiving"));
        }
        Object o = DisguisePlugin.nms.buildSpawnPacket(p, this);
        DisguisePlugin.nms.sendPacket(p, o);
    }

    public abstract DisguiseWatcher getDataWatcher();


    public int getTypeId()
    {
        return type.getTypeId();
    }

    public void disguise()
    {
        Object destroy = DisguisePlugin.nms.buildDestroy(playerId);

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != player && pl.getWorld() == player.getWorld()) {

                if (DisguisePlugin.nms.isPlayerSeeing(pl, player)) {

                    DisguisePlugin.nms.sendPacket(pl, destroy);
                    sendSpawn(pl);
                }
            }
        }

    }

    public void restore()
    {
        Object destroy = DisguisePlugin.nms.buildDestroy(playerId);
        Object spawn = DisguisePlugin.nms.buildSpawnPlayer(player);

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != player && pl.getWorld() == player.getWorld()) {

                if (DisguisePlugin.nms.isPlayerSeeing(pl, player)) {

                    DisguisePlugin.nms.sendPacket(pl, destroy);
                    DisguisePlugin.nms.sendPacket(pl, spawn);
                }
            }
        }
    }

    public Player getPlayer()
    {
        return player;
    }
}
