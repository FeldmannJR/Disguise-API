package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.annotations.SetAnnotation;
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

    @SetAnnotation(nome = "profession")
    public void setProfession(Villager.Profession prof) {
        this.prof = prof;
        watcher.add(16, prof.ordinal());
    }

    public Villager.Profession getProfession() {
        return prof;
    }

}
