package me.feldmannjr.disguise.v18;

import me.feldmannjr.disguise.DisguiseData;
import me.feldmannjr.disguise.DisguiseWatcher;
import me.feldmannjr.disguise.PluginVersion;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;

public class v1_8_R3 extends PluginVersion {
    FieldResolver spawnResolver = null;
    NMSClassResolver nmsResolver = null;

    public void sendPacket(Player p, Object packet)
    {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) packet);
    }


    public Object buildSpawnPacket(Player player, DisguiseData data)
    {
        if (nmsResolver == null) {
            nmsResolver = new NMSClassResolver();
        }
        if (spawnResolver == null) {
            spawnResolver = new FieldResolver(nmsResolver.resolveSilent("PacketPlayOutSpawnEntityLiving"));
        }
        PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving();
        Location l = player.getLocation();

        try {
            spawnResolver.resolveSilent("a").set(spawn, player.getEntityId());
            spawnResolver.resolveSilent("b").set(spawn, data.getTypeId());
            spawnResolver.resolveSilent("c").set(spawn, n(l.getX()));
            spawnResolver.resolveSilent("d").set(spawn, n(l.getY()));
            spawnResolver.resolveSilent("e").set(spawn, n(l.getZ()));
            spawnResolver.resolveSilent("i").set(spawn, m(l.getPitch()));
            spawnResolver.resolveSilent("j").set(spawn, m(l.getYaw()));
            spawnResolver.resolveSilent("k").set(spawn, m(l.getYaw()));//Head rotation
            spawnResolver.resolveSilent("f").set(spawn, v(player.getVelocity().getX()));
            spawnResolver.resolveSilent("g").set(spawn, v(player.getVelocity().getY()));
            spawnResolver.resolveSilent("h").set(spawn, v(player.getVelocity().getZ()));
            DataWatcher watcher = new DataWatcher(null);
            spawnResolver.resolveSilent("l").set(spawn, watcher);//Data watcher

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return spawn;
    }

    public Object buildSpawnPlayer(Player p)
    {
        return new PacketPlayOutNamedEntitySpawn(((CraftPlayer) p).getHandle());
    }


    public Object buildDestroy(int entityid)
    {
        return new PacketPlayOutEntityDestroy(entityid);
    }


    public boolean isPlayerSeeing(Player player, Player p2)
    {
        if (!player.canSee(p2)) {
            return false;
        }
        IntHashMap<EntityTrackerEntry> trackedEntities = ((CraftPlayer) p2).getHandle().u().getTracker().trackedEntities;
        EntityTrackerEntry en = trackedEntities.get(p2.getEntityId());
        if (en != null) {
            return en.trackedPlayers.contains(((CraftPlayer) player).getHandle());
        }

        return false;
    }

    public Object convertToNmsDatawatcher(DisguiseWatcher watcher)
    {
        DataWatcher nmswatcher = new DataWatcher(null);

        for (DisguiseWatcher.WatcherValue value : watcher.getValues().values()) {
            Object obj;
            if (value.getType() == DisguiseWatcher.WatcherValueType.ITEMSTACK) {
                obj = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) value.getValue());
            } else if (value.getType() == DisguiseWatcher.WatcherValueType.BLOCKPOS) {
                Vector vec = (Vector) value.getValue();
                obj = new BlockPosition(vec.getBlockX(), vec.getY(), vec.getZ());
            } else if (value.getType() == DisguiseWatcher.WatcherValueType.ROTATIONS) {
                DisguiseWatcher.Rotation r = (DisguiseWatcher.Rotation) value.getValue();
                obj = new Vector3f(r.getX(), r.getY(), r.getZ());
            } else {
                obj = value.getValue();
            }
            nmswatcher.watch(value.getKey(), obj);

        }
        return nmswatcher;
    }


    public DisguiseWatcher convertFromNmsDatawatcher(Object watcher)
    {
        DisguiseWatcher nwatcher = new DisguiseWatcher();

        DataWatcher nmswatcher = (DataWatcher) watcher;
        for (DataWatcher.WatchableObject value : nmswatcher.c()) {
            int type = value.c();
            int key = value.a();
            Object obj = value.b();
            Object nobj;
            if (type == 5) {
                nobj = CraftItemStack.asBukkitCopy((ItemStack) obj);
            } else if (type == 6) {
                BlockPosition position = (BlockPosition) obj;
                nobj = new Vector(position.getX(), position.getY(), position.getZ());
            } else if (type == 7) {
                Vector3f vector = (Vector3f) obj;
                nobj = new DisguiseWatcher.Rotation(vector.getX(), vector.getY(), vector.getZ());
            } else {
                nobj = obj;
            }
            nwatcher.add(key, nobj);

        }


        return null;
    }


}
