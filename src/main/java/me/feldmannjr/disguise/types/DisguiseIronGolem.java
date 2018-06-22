package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseIronGolem extends LivingData {
    public DisguiseIronGolem(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.IRON_GOLEM;
    }
}
