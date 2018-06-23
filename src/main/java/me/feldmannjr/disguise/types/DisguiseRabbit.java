package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.annotations.ActionAnnotation;
import me.feldmannjr.disguise.annotations.SetAnnotation;
import me.feldmannjr.disguise.types.base.AgeableData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;

public class DisguiseRabbit extends AgeableData {

    Rabbit.Type type = Rabbit.Type.BROWN;

    public DisguiseRabbit(Player p) {
        super(p);
    }

    public EntityType getEntityType() {
        return EntityType.RABBIT;
    }

    @SetAnnotation(nome = "rabittype")
    public void setType(Rabbit.Type type) {
        watcher.add(18, (byte) type.ordinal());
        this.type = type;
    }

    public Rabbit.Type getRabbitType() {
        return type;
    }

    @ActionAnnotation
    public void jump() {
        doStatus((byte) 1);
    }

}
