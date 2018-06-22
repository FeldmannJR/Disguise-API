package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.DisguiseWatcher;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LivingData extends DisguiseData {

    DisguiseWatcher watcher = new DisguiseWatcher();

    public LivingData(Player p, EntityType type) {
        super(p, type);
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
        if (nameTag != null) {
            watcher.add(2, nameTag);
        }
        watcher.add(9, arrows);
    }

    public DisguiseWatcher getDataWatcher() {
        return watcher;
    }

}
