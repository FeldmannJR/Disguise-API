package me.feldmannjr.disguise.types.base;

import me.feldmannjr.disguise.annotations.SetAnnotation;
import org.bukkit.entity.Player;

public abstract class AgeableData extends LivingData {
    boolean baby = false;

    public AgeableData(Player p) {
        super(p);
    }

    @SetAnnotation(nome = "baby")
    public void setBaby(boolean baby) {
        this.baby = baby;
        watcher.add(12, baby ? (byte) -1 : (byte) 0);
    }

    public boolean isBaby() {
        return baby;
    }

}
