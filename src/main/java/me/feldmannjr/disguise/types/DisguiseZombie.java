package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.EquipmentData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseZombie extends EquipmentData {

    private boolean baby = false;
    private boolean villager = false;

    public DisguiseZombie(Player p) {
        super(p);
        updateZombieWatcher();
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
        updateZombieWatcher();
        sendWatcher();
    }

    private void updateZombieWatcher() {
        watcher.add(12, (byte) (baby ? 1 : 0));
        watcher.add(13, (byte) (villager ? 1 : 0));

    }

    public void setVillager(boolean villager) {
        this.villager = villager;
        updateZombieWatcher();
        sendWatcher();
    }

    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("villager")) {
            setVillager(!villager);
            return true;
        }
        if (opt.equalsIgnoreCase("baby")) {
            setBaby(!baby);
            return true;
        }
        return super.processOpt(opt);
    }

    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

}
