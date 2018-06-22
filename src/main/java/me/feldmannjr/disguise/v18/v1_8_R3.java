package me.feldmannjr.disguise.v18;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.IntHashMap;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.Vector3f;

import me.feldmannjr.disguise.types.base.DisguiseData;
import me.feldmannjr.disguise.DisguiseWatcher;
import me.feldmannjr.disguise.PluginVersion;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;
import java.util.Map;

public class v1_8_R3 extends PluginVersion {
    FieldResolver spawnResolver = null;
    NMSClassResolver nmsResolver = null;
    FieldResolver attachResolver;

    public void sendPacket(Player p, Object packet) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) packet);
    }

    public Object buildSpawnPacket(Player player, DisguiseData data) {
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
            spawnResolver.resolveSilent("b").set(spawn, data.getEntityType().getTypeId());
            spawnResolver.resolveSilent("c").set(spawn, n(l.getX()));
            spawnResolver.resolveSilent("d").set(spawn, n(l.getY()));
            spawnResolver.resolveSilent("e").set(spawn, n(l.getZ()));
            spawnResolver.resolveSilent("i").set(spawn, m(l.getPitch()));
            spawnResolver.resolveSilent("j").set(spawn, m(l.getYaw()));
            spawnResolver.resolveSilent("k").set(spawn, m(l.getYaw()));//Head rotation
            spawnResolver.resolveSilent("f").set(spawn, v(player.getVelocity().getX()));
            spawnResolver.resolveSilent("g").set(spawn, v(player.getVelocity().getY()));
            spawnResolver.resolveSilent("h").set(spawn, v(player.getVelocity().getZ()));
            DataWatcher watcher = (DataWatcher) convertToNmsDatawatcher(data.getDataWatcher());
            spawnResolver.resolveSilent("l").set(spawn, watcher);//Data watcher

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return spawn;
    }

    public Object buildSpawnPlayer(Player p) {
        return new PacketPlayOutNamedEntitySpawn(((CraftPlayer) p).getHandle());
    }

    public Object buildDestroy(int... entityid) {
        return new PacketPlayOutEntityDestroy(entityid);
    }

    public Object buildEquipment(int entityid, int slot, org.bukkit.inventory.ItemStack item) {

        return new PacketPlayOutEntityEquipment(entityid, slot, CraftItemStack.asNMSCopy(item));

    }

    public Object buildMetadata(int entityid, DisguiseWatcher watcher) {
        return new PacketPlayOutEntityMetadata(entityid, (DataWatcher) convertToNmsDatawatcher(watcher), true);
    }

    public Object buildMount(int vehicle, int passanger) {
        if (attachResolver == null) {
            attachResolver = new FieldResolver(nmsResolver.resolveSilent("PacketPlayOutAttachEntity"));
        }
        Packet p = new PacketPlayOutAttachEntity();
        try {
            attachResolver.resolveSilent("b").set(p, passanger);
            attachResolver.resolveSilent("c").set(p, vehicle);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return p;
    }

    public Object buildSpawnPacket(int id, EntityType type, Location l, DisguiseWatcher watcher) {
        if (nmsResolver == null) {
            nmsResolver = new NMSClassResolver();
        }
        if (spawnResolver == null) {
            spawnResolver = new FieldResolver(nmsResolver.resolveSilent("PacketPlayOutSpawnEntityLiving"));
        }
        PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving();

        try {
            spawnResolver.resolveSilent("a").set(spawn, id);
            spawnResolver.resolveSilent("b").set(spawn, type.getTypeId());
            spawnResolver.resolveSilent("c").set(spawn, n(l.getX()));
            spawnResolver.resolveSilent("d").set(spawn, n(l.getY()));
            spawnResolver.resolveSilent("e").set(spawn, n(l.getZ()));
            spawnResolver.resolveSilent("i").set(spawn, m(l.getPitch()));
            spawnResolver.resolveSilent("j").set(spawn, m(l.getYaw()));
            spawnResolver.resolveSilent("k").set(spawn, m(l.getYaw()));//Head rotation
            spawnResolver.resolveSilent("f").set(spawn, v(0));
            spawnResolver.resolveSilent("g").set(spawn, v(0));
            spawnResolver.resolveSilent("h").set(spawn, v(0));
            spawnResolver.resolveSilent("l").set(spawn, convertToNmsDatawatcher(watcher));//Data watcher

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return spawn;
    }

    public boolean isPlayerSeeing(Player player, Player p2) {
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

    FieldResolver dataWatcherSolver;

    public Object convertToNmsDatawatcher(DisguiseWatcher watcher) {
        if (dataWatcherSolver == null) {
            dataWatcherSolver = new FieldResolver(nmsResolver.resolveSilent("DataWatcher"));
        }

        DataWatcher nmswatcher = new DataWatcher(null);
        Map<Integer, DataWatcher.WatchableObject> map;
        try {
            map = (Map<Integer, DataWatcher.WatchableObject>) dataWatcherSolver.resolveSilent("d").get(nmswatcher);
        } catch (IllegalAccessException e) {
            return nmswatcher;
        }

        for (DisguiseWatcher.WatcherValue value : watcher.getValues().values()) {
            Object obj = value.getValue();
            int type = 0;
            if (value.getValue() == null) {
                continue;
            }
            switch (value.getType()) {
                case BYTE:
                    type = 0;
                    break;
                case SHORT:
                    type = 1;
                    break;
                case INTEGER:
                    type = 2;
                    break;
                case FLOAT:
                    type = 3;
                    break;
                case STRING:
                    type = 4;
                    break;
                case ITEMSTACK:
                    obj = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) value.getValue());
                    type = 5;
                    break;
                case BLOCKPOS:
                    Vector vec = (Vector) value.getValue();
                    obj = new BlockPosition(vec.getBlockX(), vec.getY(), vec.getZ());
                    type = 6;
                    break;
                case ROTATIONS:
                    DisguiseWatcher.Rotation r = (DisguiseWatcher.Rotation) value.getValue();
                    obj = new Vector3f(r.getX(), r.getY(), r.getZ());
                    type = 7;
                    break;
                default:
                    obj = value.getValue();
                    break;

            }

            map.put(value.getKey(), new DataWatcher.WatchableObject(type, value.getKey(), obj));

        }
        return nmswatcher;
    }

    public DisguiseWatcher convertFromNmsDatawatcher(Object watcher) {
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
