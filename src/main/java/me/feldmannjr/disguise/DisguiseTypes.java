package me.feldmannjr.disguise;

import org.bukkit.entity.EntityType;

public enum DisguiseTypes {

    ZOMBIE(EntityType.ZOMBIE);

    EntityType type;

    private DisguiseTypes(EntityType type) {
        this.type = type;

    }
}
