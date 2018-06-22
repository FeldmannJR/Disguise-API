package me.feldmannjr.disguise.types;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSkeleton extends EquipmentData {

    boolean wither = false;

    public DisguiseSkeleton(Player p) {
        super(p, EntityType.SKELETON);
    }

    public void setWither(boolean wither) {
        this.wither = wither;
        watcher.add(13, (byte) (wither ? 1 : 0));
        sendWatcher();
    }
}
