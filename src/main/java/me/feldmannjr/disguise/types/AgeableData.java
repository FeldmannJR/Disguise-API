package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.DisguiseWatcher;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AgeableData extends LivingData {

    private boolean baby = true;

    public AgeableData(Player p, EntityType type)
    {
        super(p, type);
    }

    @Override
    public DisguiseWatcher getDataWatcher()
    {
        DisguiseWatcher w = super.getDataWatcher();
        w.add(12, (byte) (baby ? 1 : 0));
        return w;
    }


}
