package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseCaveSpider extends LivingData {
    public DisguiseCaveSpider(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.CAVE_SPIDER;
    }
}
