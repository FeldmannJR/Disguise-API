package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseSlime extends LivingData {

    byte size = 0;

    public DisguiseSlime(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.SLIME;
    }

    public void setSize(byte size) {
        this.size = size;
        watcher.add(16, size);
        sendWatcher();
    }

    public byte getSize() {
        return size;
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("size")) {
            byte s = (byte) (1 + size);
            if (s > 5) {
                s = -1;
            }
            setSize(s);
            return true;
        }
        return super.processOpt(opt);
    }
}
