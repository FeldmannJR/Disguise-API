package me.feldmannjr.disguise;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class DisguiseAPI {

    private static HashMap<UUID, DisguiseData> disguises = new HashMap();


    public static DisguiseData getDisguise(Player p)
    {
        return getDisguise(p.getUniqueId());

    }

    public static void setDisguise(Player p, DisguiseData data)
    {
        if (data == null) {
            disguises.remove(p.getUniqueId());
        } else {
            disguises.put(p.getUniqueId(), data);
        }


    }


    public static DisguiseData getDisguise(UUID uid)
    {
        if (disguises.containsKey(uid)) {
            return disguises.get(uid);
        }
        return null;
    }
}
