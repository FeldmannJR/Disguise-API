package me.feldmannjr.disguise.types;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseZombie extends EquipmentData {

    private boolean baby = true;
    private boolean villager = true;

    public DisguiseZombie(Player p) {
        super(p, EntityType.ZOMBIE);
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

}
