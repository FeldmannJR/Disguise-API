package me.feldmannjr.disguise.types.base;

import me.feldmannjr.disguise.DisguisePlugin;
import me.feldmannjr.disguise.PluginVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DisguiseData {

    UUID uid;
    int playerId;
    Player player;
    protected boolean disguised = false;

    public DisguiseData(Player p) {
        this.player = p;
        this.uid = p.getUniqueId();
        this.playerId = p.getEntityId();

    }

    public List<Player> getSeeing() {
        List<Player> seeing = new ArrayList();
        for (Player pl: Bukkit.getOnlinePlayers()) {
            if (pl != player && pl.getWorld() == player.getWorld()) {
                if (DisguisePlugin.nms.isPlayerSeeing(pl, player)) {
                    seeing.add(pl);
                }
            }
        }
        return seeing;

    }

    public PluginVersion nms() {
        return DisguisePlugin.nms;

    }

    public boolean processOpt(String opt) {
        return false;
    }

    public boolean isDisguised() {
        return disguised;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void restore();

    public abstract void disguise();

}
