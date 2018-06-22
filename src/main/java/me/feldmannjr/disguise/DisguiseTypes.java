package me.feldmannjr.disguise;

import me.feldmannjr.disguise.types.DisguiseCreeper;
import me.feldmannjr.disguise.types.base.DisguiseData;
import me.feldmannjr.disguise.types.DisguiseSkeleton;
import me.feldmannjr.disguise.types.DisguiseZombie;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;

public enum DisguiseTypes {

    ZOMBIE(DisguiseZombie.class),
    CREEPER(DisguiseCreeper.class),
    SKELETON(DisguiseSkeleton.class);

    Class<? extends DisguiseData> classe;

    private DisguiseTypes(Class<? extends DisguiseData> classe) {
        this.classe = classe;
    }

    public DisguiseData createData(Player p) {
        try {
            return classe.getConstructor(Player.class).newInstance(p);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
