package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.DisguiseData;
import me.feldmannjr.disguise.DisguiseWatcher;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LivingData extends DisguiseData {

    public LivingData(Player p, EntityType type)
    {
        super(p, type);
        onFire = p.getFireTicks() > 10;
        sneaking = p.isSneaking();
        sprinting = p.isSprinting();
        nameTag = p.getName();
    }

    private boolean onFire = false;
    private boolean sneaking = false;
    private boolean sprinting = false;
    private boolean eating = false;
    private boolean invisible = false;
    private String nameTag = null;
    private byte arrows = 0;


    public DisguiseWatcher getDataWatcher()
    {
        DisguiseWatcher watcher = new DisguiseWatcher();
        //http://wiki.vg/index.php?title=Entity_metadata&oldid=7415
        //https://stackoverflow.com/questions/17256644/how-does-the-bitwise-and-work-in-java = Faculdade servindo pra algo
        watcher.add(0, (byte) ~((byte) (0x01) | (onFire ? 1 : 0) | (byte) (0x02) | (sneaking ? 1 : 0) | (byte) (0x08) | (sprinting ? 1 : 0) | (byte) (0x10) | (eating ? 1 : 0) | (byte) (0x20) | (invisible ? 1 : 0)));
        if (nameTag != null) {
            watcher.add(2, nameTag);
        }
        watcher.add(9, arrows);

        return watcher;
    }


}
