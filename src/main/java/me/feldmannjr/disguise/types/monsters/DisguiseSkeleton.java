package me.feldmannjr.disguise.types.monsters;

import me.feldmannjr.disguise.annotations.SetAnnotation;
import me.feldmannjr.disguise.types.base.EquipmentData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSkeleton extends EquipmentData {

    boolean wither = false;

    public DisguiseSkeleton(Player p) {
        super(p);
    }

    @SetAnnotation(nome = "wither")
    public void setWither(boolean wither) {
        this.wither = wither;
        watcher.add(13, (byte) (wither ? 1 : 0));
    }

    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

}
