package me.feldmannjr.disguise.types.base;

import me.feldmannjr.disguise.DisguisePlugin;
import me.feldmannjr.disguise.DisguiseWatcher;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public abstract class LivingData extends DisguiseData {

    public DisguiseWatcher watcher = new DisguiseWatcher();

    public LivingData(Player p) {
        super(p);
        onFire = p.getFireTicks() > 10;
        sneaking = p.isSneaking();
        sprinting = p.isSprinting();
        nameTag = p.getName();
        updateWatcher();

    }

    private boolean onFire = false;
    private boolean sneaking = false;
    private boolean sprinting = false;
    private boolean eating = false;
    private boolean invisible = false;
    private String nameTag = null;
    private byte arrows = 0;

    private void updateWatcher() {
        watcher.add(0, (byte) ~((byte) (0x01) | (onFire ? 1 : 0) | (byte) (0x02) | (sneaking ? 1 : 0) | (byte) (0x08) | (sprinting ? 1 : 0) | (byte) (0x10) | (eating ? 1 : 0) | (byte) (0x20) | (invisible ? 1 : 0)));
        watcher.add(9, arrows);
    }

    private Object buildArmorStandPacket() {
        DisguiseWatcher watcher = new DisguiseWatcher();
        //marker //baspalte //arms //gravity //small
        byte b = Byte.parseByte("10000", 2);
        watcher.add(10, b);
        watcher.add(2, nameTag);
        watcher.add(3, (byte) 1);
        watcher.add(0, (byte) 0x20);
        return DisguisePlugin.nms.buildSpawnPacket(getNameId(), EntityType.ARMOR_STAND, getPlayer().getLocation(), watcher);
    }

    private Object buildSquid() {
        DisguiseWatcher watcher = new DisguiseWatcher();
        watcher.add(0, (byte) 0x20);
        //   watcher.add(16, (byte) -2);
        return DisguisePlugin.nms.buildSpawnPacket(getNameId() + 1, EntityType.WOLF, getPlayer().getLocation(), watcher);
    }

    public void destroyArmorStand(Player p) {
        DisguisePlugin.nms.sendPacket(p, DisguisePlugin.nms.buildDestroy(getNameId(), getNameId() + 1));

    }

    public int getNameId() {
        return Integer.MAX_VALUE - 1000 - (player.getEntityId() * 2);

    }

    public DisguiseWatcher getDataWatcher() {
        return watcher;
    }

    public void sendSpawn(Player p) {
        Object o = DisguisePlugin.nms.buildSpawnPacket(player, this);
        DisguisePlugin.nms.sendPacket(p, o);
        if (nameTag == null) {
            return;
        }

        DisguisePlugin.nms.sendPacket(p, buildArmorStandPacket());
        DisguisePlugin.nms.sendPacket(p, buildSquid());
        DisguisePlugin.nms.sendPacket(p, DisguisePlugin.nms.buildMount(getPlayer().getEntityId(), getNameId() + 1));
        DisguisePlugin.nms.sendPacket(p, DisguisePlugin.nms.buildMount(getNameId() + 1, getNameId()));

    }

    public void disguise() {
        Object destroy = DisguisePlugin.nms.buildDestroy(playerId);
        disguised = true;
        for (Player pl : getSeeing()) {
            DisguisePlugin.nms.sendPacket(pl, destroy);
            sendSpawn(pl);
        }
    }

    public void sendWatcher() {
        Object packet = DisguisePlugin.nms.buildMetadata(getPlayer().getEntityId(), getDataWatcher());
        for (Player p : getSeeing()) {
            DisguisePlugin.nms.sendPacket(p, packet);
        }

    }

    public void restore() {
        Object destroy = DisguisePlugin.nms.buildDestroy(playerId);
        Object spawn = DisguisePlugin.nms.buildSpawnPlayer(player);

        for (Player pl : getSeeing()) {
            DisguisePlugin.nms.sendPacket(pl, destroy);
            DisguisePlugin.nms.sendPacket(pl, spawn);
        }
        for (Player p : getSeeing()) {
            destroyArmorStand(p);
        }
        disguised = false;
    }

    public abstract EntityType getEntityType();

}
