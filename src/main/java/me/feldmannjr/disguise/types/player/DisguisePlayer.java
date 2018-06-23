package me.feldmannjr.disguise.types.player;

import net.minecraft.server.v1_8_R3.EntityPlayer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import me.feldmannjr.disguise.DisguisePlugin;
import me.feldmannjr.disguise.types.base.DisguiseData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

public class DisguisePlayer extends DisguiseData {

    public PlayerDisguiseData data;

    public DisguisePlayer(Player p, String nome) {
        super(p);
        data = new PlayerDisguiseData(p, nome);

    }

    @Override
    public void restore() {
        removeFromTab(true);
        //      injectProperties(nms().getGameProfile(getPlayer()), data.texturedefault, getPlayer().getName());
        //  getPlayer().setCustomName(null);
        nms().addToTabList(getPlayer());
        respawn();
    }

    public void removeFromTab(boolean disguised) {
        Object other = nms().removeFromTabList(data.getUUID());
        Object self = nms().removeFromTabList(getPlayer().getUniqueId());
        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p != getPlayer() && disguised) {
                nms().sendPacket(p, other);
            } else {
                nms().sendPacket(p, self);
            }
        }
    }

    public void respawn() {
        Object destroy = nms().buildDestroy(getPlayer().getEntityId());
        Object spawn = nms().buildSpawnPlayer(getPlayer());
        for (Player p: getSeeing()) {
            nms().sendPacket(p, destroy);
            nms().sendPacket(p, spawn);
        }
        EntityPlayer handle = ((CraftPlayer) getPlayer()).getHandle();
        ((CraftPlayer) getPlayer()).getHandle().server.getPlayerList().moveToWorld(handle, 0, false, handle.getBukkitEntity().getLocation(), true);
    }

    @Override
    public void disguise() {

        removeFromTab(false);
        nms().addToTabList(getPlayer());
        //   getPlayer().setCustomName(data.nome);
        respawn();
    }

    GameProfile cache;

    public GameProfile create(boolean others) {

        if (others && cache != null) {
            return cache;
        }
        GameProfile profile;
        if (others) {
            profile = new GameProfile(data.uid, data.nome);
        } else {
            profile = new GameProfile(getPlayer().getUniqueId(), data.nome);
        }

        try {
            Field fProperties = profile.getClass().getDeclaredField("properties");
            fProperties.setAccessible(true);
            Field name = profile.getClass().getDeclaredField("name");
            name.setAccessible(true);
            try {
                name.set(profile, data.nome);
                fProperties.set(profile, SkinDownloader.getTexture(data.nome));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (cache == null && others) {
            cache = profile;
        }

        return profile;
    }

    public class PlayerDisguiseData {
        String nome;
        String prefix = null;
        String sufix = null;
        Property texturedefault;
        Player p;
        UUID uid;

        public PlayerDisguiseData(Player p, String nome) {
            this.p = p;
            this.nome = nome;
            PropertyMap map = DisguisePlugin.nms.getGameProfile(p).getProperties();
            for (String s: map.keys()) {
                Collection<Property> properties = map.get(s);
            }
            uid = UUID.randomUUID();
        }

        public UUID getUUID() {
            return uid;
        }
    }

}
