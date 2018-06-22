package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseCreeper extends LivingData {

    boolean powered = false;//17
    boolean fuse = false;//16

    public DisguiseCreeper(Player p) {
        super(p);
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

    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("fuse")) {
            setFuse(!fuse);
            return true;
        }
        if (opt.equalsIgnoreCase("powered")) {
            setPowered(!powered);
            return true;
        }
        return super.processOpt(opt);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CREEPER;
    }
}
