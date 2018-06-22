package me.feldmannjr.disguise;

import me.feldmannjr.disguise.types.base.DisguiseData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DisguiseAPI {

    private static HashMap<UUID, DisguiseData> disguises = new HashMap();

    public static DisguiseData getDisguise(Player p) {
        return getDisguise(p.getUniqueId());

    }

    public static void setDisguise(Player p, DisguiseData data) {
        DisguiseData datao = disguises.remove(p.getUniqueId());
        if (datao != null) {
            datao.restore();
        }
        if (data != null) {
            disguises.put(p.getUniqueId(), data);
            data.disguise();
        }
    }

    public static DisguiseData getDisguise(UUID uid) {
        if (disguises.containsKey(uid)) {
            return disguises.get(uid);
        }
        return null;
    }
}
