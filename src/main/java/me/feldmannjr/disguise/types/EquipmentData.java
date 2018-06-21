package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.DisguiseData;
import me.feldmannjr.disguise.DisguiseWatcher;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EquipmentData extends DisguiseData {

    public EquipmentData(Player p, EntityType type)
    {
        super(p, type);
    }

    public DisguiseWatcher getDataWatcher()
    {
        return null;
    }
}
