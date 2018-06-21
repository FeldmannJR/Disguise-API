package me.feldmannjr.disguise;

import org.bukkit.entity.Player;

//Reflections é um saco melhor criar uma versão pra cada
public abstract class PluginVersion {


    public abstract void sendPacket(Player p, Object packet);

    public abstract Object buildSpawnPacket(Player p, DisguiseData data);

    public abstract Object buildSpawnPlayer(Player p);

    public abstract Object buildDestroy(int entityid);

    /*
     * Is @player seeing @p2?
     *
     * */
    public abstract boolean isPlayerSeeing(Player player, Player p2);

    public abstract Object convertToNmsDatawatcher(DisguiseWatcher watcher);

    public abstract DisguiseWatcher convertFromNmsDatawatcher(Object watcher);


    public int n(double d)
    {
        return (int) Math.floor(d * 32);
    }

    public int v(double f)
    {
        return (int) (8000D * Math.max(-3.9F, Math.min(f, 3.9D)));

    }

    public byte m(float f)
    {
        return (byte) d(f * 256F / 360F);
    }

    public static int d(float var0)
    {
        int var1 = (int) var0;
        return var0 < (float) var1 ? var1 - 1 : var1;
    }


}
