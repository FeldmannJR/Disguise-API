package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.EquipmentData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSkeleton extends EquipmentData {

    boolean wither = false;

    public DisguiseSkeleton(Player p) {
        super(p);
    }

    public void setWither(boolean wither) {
        this.wither = wither;
        watcher.add(13, (byte) (wither ? 1 : 0));
        sendWatcher();
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("wither")) {
            setWither(!wither);
            return true;
        }
        return super.processOpt(opt);
    }

    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

}
