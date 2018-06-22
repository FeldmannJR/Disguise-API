package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.AgeableData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class DisguiseVillager extends AgeableData {

    Villager.Profession prof = Villager.Profession.FARMER;

    public DisguiseVillager(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.VILLAGER;
    }

    public void setProfession(Villager.Profession prof) {
        this.prof = prof;
        watcher.add(16, prof.ordinal());
        sendWatcher();
    }

    public Villager.Profession getProfession() {
        return prof;
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("profession")) {
            setProfession(Villager.Profession.values()[prof.ordinal() >= Villager.Profession.values().length ? 0 : (prof.ordinal() + 1)]);
            return true;
        }
        return super.processOpt(opt);
    }
}
