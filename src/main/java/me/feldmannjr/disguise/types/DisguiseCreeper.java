package me.feldmannjr.disguise.types;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseCreeper extends LivingData {

    boolean powered = false;//17
    boolean fuse = true;//16

    public DisguiseCreeper(Player p) {
        super(p, EntityType.CREEPER);
    }

    public void setFuse(boolean fuse) {
        this.fuse = fuse;
        watcher.add(16, (byte) (fuse ? 1 : -1));
        sendWatcher();
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
        watcher.add(17, (byte) (powered ? 1 : 0));
        sendWatcher();
    }

    public boolean isFuse() {
        return fuse;
    }

    public boolean isPowered() {
        return powered;
    }
}
