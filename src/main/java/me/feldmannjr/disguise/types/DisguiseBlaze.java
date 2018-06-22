package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseBlaze extends LivingData {

    boolean onfire = false;

    public DisguiseBlaze(Player p) {
        super(p);
    }

    public void setOnfire(boolean onfire) {
        this.onfire = onfire;
        watcher.add(16, onfire ? (byte) 1 : (byte) 0);
        sendWatcher();
    }

    public boolean isOnfire() {
        return onfire;
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("fire")) {
            setOnfire(!isOnfire());
            return true;
        }
        return super.processOpt(opt);
    }

    public EntityType getEntityType() {
        return EntityType.BLAZE;
    }

}
