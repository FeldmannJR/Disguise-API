package me.feldmannjr.disguise;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;

import java.lang.reflect.Field;
import java.util.UUID;

public class DisguiseData {

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
            spawnResolver = new FieldResolver(nmsResolver.resolveSilent("PacketPlayOutSpawnEntity"));
        }
        PacketPlayOutSpawnEntity spawn = new PacketPlayOutSpawnEntity();
        Location l = p.getLocation();
        try {
            spawnResolver.resolveSilent("a").set(spawn, playerId);
            spawnResolver.resolveSilent("b").set(spawn, type.getTypeId());
            spawnResolver.resolveSilent("c").set(spawn, n(l.getX()));
            spawnResolver.resolveSilent("d").set(spawn, n(l.getY()));
            spawnResolver.resolveSilent("e").set(spawn, n(l.getZ()));
            spawnResolver.resolveSilent("i").set(spawn, m(l.getPitch()));
            spawnResolver.resolveSilent("j").set(spawn, m(l.getYaw()));
            spawnResolver.resolveSilent("k").set(spawn, m(l.getYaw()));//Head rotation
            spawnResolver.resolveSilent("f").set(spawn, v(p.getVelocity().getX()));
            spawnResolver.resolveSilent("g").set(spawn, v(p.getVelocity().getY()));
            spawnResolver.resolveSilent("h").set(spawn, v(p.getVelocity().getZ()));
            DataWatcher watcher = new DataWatcher(null);
            spawnResolver.resolveSilent("l").set(playerId, watcher);//Data watcher

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sendPacket(p, spawn);
    }

    private int n(double d)
    {
        return (int) Math.floor(d * 32);
    }

    int v(double f)
    {
        return (int) (8000D * Math.max(-3.9F, Math.min(f, 3.9D)));

    }

    int m(float f)
    {
        return d(f * 256F / 360F);
    }

    public static int d(float var0)
    {
        int var1 = (int) var0;
        return var0 < (float) var1 ? var1 - 1 : var1;
    }


    private void sendPacket(Player p, Packet b)
    {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(b);
    }


}
