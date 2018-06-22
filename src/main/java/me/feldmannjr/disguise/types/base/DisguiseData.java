package me.feldmannjr.disguise.types.base;

import me.feldmannjr.disguise.DisguisePlugin;
import me.feldmannjr.disguise.DisguiseWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DisguiseData {

    UUID uid;
    int playerId;
    Player player;
    private boolean disguised = false;

    public DisguiseData(Player p) {
        this.player = p;
        this.uid = p.getUniqueId();
        this.playerId = p.getEntityId();

    }

    public void sendSpawn(Player p) {

        Object o = DisguisePlugin.nms.buildSpawnPacket(player, this);
        DisguisePlugin.nms.sendPacket(p, o);
    }

    public void disguise() {
        Object destroy = DisguisePlugin.nms.buildDestroy(playerId);
        disguised = true;
        for (Player pl : getSeeing()) {
            DisguisePlugin.nms.sendPacket(pl, destroy);
            sendSpawn(pl);
        }
    }

    public void sendWatcher() {
        Object packet = DisguisePlugin.nms.buildMetadata(getPlayer().getEntityId(), getDataWatcher());
        for (Player p : getSeeing()) {
            DisguisePlugin.nms.sendPacket(p, packet);
        }

    }

    public void restore() {
        Object destroy = DisguisePlugin.nms.buildDestroy(playerId);
        Object spawn = DisguisePlugin.nms.buildSpawnPlayer(player);

        for (Player pl : getSeeing()) {
            DisguisePlugin.nms.sendPacket(pl, destroy);
            DisguisePlugin.nms.sendPacket(pl, spawn);
        }
        disguised = false;
    }

    public List<Player> getSeeing() {
        List<Player> seeing = new ArrayList();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != player && pl.getWorld() == player.getWorld()) {
                if (DisguisePlugin.nms.isPlayerSeeing(pl, player)) {
                    seeing.add(pl);
                }
            }
        }
        return seeing;

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

    public abstract DisguiseWatcher getDataWatcher();

    public abstract EntityType getEntityType();

}
