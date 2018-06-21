package me.feldmannjr.disguise;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class DisguiseWatcher {

    HashMap<Integer, WatcherValue> values = new HashMap();

    public HashMap<Integer, WatcherValue> getValues()
    {
        return values;
    }

    public void add(int key, Object obj)
    {
        for (WatcherValueType typ : WatcherValueType.values()) {
            if (typ.type == obj.getClass()) {
                System.out.println(typ.name() + " " + key + obj.getClass().getSimpleName());
                values.put(key, new WatcherValue(key, typ, obj));
                break;
            }
        }


    }


    public enum WatcherValueType {
        BYTE(Byte.class),
        SHORT(Short.class),
        INTEGER(Integer.class),
        FLOAT(Float.class),
        STRING(String.class),
        ITEMSTACK(ItemStack.class),
        BLOCKPOS(Vector.class),
        ROTATIONS(Rotation.class);

        Class type;

        private WatcherValueType(Class type)
        {
            this.type = type;

        }
    }


    public class WatcherValue {
        int key;
        WatcherValueType type;
        Object value;

        public WatcherValue(int key, WatcherValueType type, Object value)
        {
            this.key = key;
            this.type = type;
            this.value = value;
        }

        public Object getValue()
        {
            return value;
        }

        public WatcherValueType getType()
        {
            return type;
        }

        public int getKey()
        {
            return key;
        }
    }

    public static class Rotation {
        private float x, y, z;

        public Rotation(float x, float y, float z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX()
        {
            return x;
        }

        public float getY()
        {
            return y;
        }

        public float getZ()
        {
            return z;
        }
    }

}
