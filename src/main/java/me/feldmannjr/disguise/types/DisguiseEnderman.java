package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class DisguiseEnderman extends LivingData {

    boolean screaming = false;
    MaterialData bloco;

    public DisguiseEnderman(Player p) {
        super(p);
    }

    public boolean isScreaming() {
        return screaming;
    }

    public void setScreaming(boolean screaming) {
        watcher.add(18, screaming ? (byte) 1 : (byte) 0);
        this.screaming = screaming;
        sendWatcher();
    }

    public void setBloco(MaterialData bloco) {
        watcher.add(16, (short) bloco.getItemTypeId());
        watcher.add(17, bloco.getData());
        this.bloco = bloco;
        sendWatcher();
    }

    public MaterialData getBloco() {
        return bloco;
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("scream")) {
            setScreaming(!isScreaming());
            return true;
        }
        if (opt.equalsIgnoreCase("bloco")) {
            setBloco(new MaterialData(Material.DIAMOND_BLOCK));
        }

        return super.processOpt(opt);
    }

    public EntityType getEntityType() {
        return EntityType.ENDERMAN;
    }
}
