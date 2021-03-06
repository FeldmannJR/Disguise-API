package me.feldmannjr.disguise;

import me.feldmannjr.disguise.types.DisguiseRabbit;
import me.feldmannjr.disguise.types.monsters.DisguiseBlaze;
import me.feldmannjr.disguise.types.monsters.DisguiseCaveSpider;
import me.feldmannjr.disguise.types.monsters.DisguiseCreeper;
import me.feldmannjr.disguise.types.monsters.DisguiseEnderman;
import me.feldmannjr.disguise.types.DisguiseIronGolem;
import me.feldmannjr.disguise.types.monsters.DisguiseSlime;
import me.feldmannjr.disguise.types.monsters.DisguiseSpider;
import me.feldmannjr.disguise.types.DisguiseVillager;
import me.feldmannjr.disguise.types.monsters.DisguiseZombiePigman;
import me.feldmannjr.disguise.types.base.DisguiseData;
import me.feldmannjr.disguise.types.monsters.DisguiseSkeleton;
import me.feldmannjr.disguise.types.monsters.DisguiseZombie;
import org.bukkit.entity.Player;
import java.lang.reflect.InvocationTargetException;

public enum DisguiseTypes {

    ZOMBIE(DisguiseZombie.class),
    CREEPER(DisguiseCreeper.class),
    SKELETON(DisguiseSkeleton.class),
    SPIDER(DisguiseSpider.class),
    CAVESPIDER(DisguiseCaveSpider.class),
    SLIME(DisguiseSlime.class),
    VILLAGER(DisguiseVillager.class),
    PIGZOMBIE(DisguiseZombiePigman.class),
    IRON_GOLEM(DisguiseIronGolem.class),
    ENDERMAN(DisguiseEnderman.class),
    BLAZE(DisguiseBlaze.class),
    RABBIT(DisguiseRabbit.class);

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
