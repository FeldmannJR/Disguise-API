package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSpider extends LivingData {
    public DisguiseSpider(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.SPIDER;
    }
}
