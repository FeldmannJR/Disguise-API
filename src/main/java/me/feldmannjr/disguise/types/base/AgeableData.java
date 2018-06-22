package me.feldmannjr.disguise.types.base;

import org.bukkit.entity.Player;

public abstract class AgeableData extends LivingData {
    boolean baby = false;

    public AgeableData(Player p) {
        super(p);
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
        watcher.add(12, baby ? (byte) -1 : (byte) 0);
    }

    public boolean isBaby() {
        return baby;
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("baby")) {
            setBaby(!isBaby());
        }
        return super.processOpt(opt);
    }
}
