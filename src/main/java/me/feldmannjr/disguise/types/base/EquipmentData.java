package me.feldmannjr.disguise.types.base;

import me.feldmannjr.disguise.DisguisePlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public abstract class EquipmentData extends LivingData {

    boolean showPlayerEquipment = true;
    boolean useCustomEquipment = false;
    ItemStack[] equipment = new ItemStack[5];

    public EquipmentData(Player p) {
        super(p);
    }

    @Override
    public void sendSpawn(Player p) {
        super.sendSpawn(p);
        showEquipment(p, false);
    }

    public void showEquipment(Player p, boolean remove) {
        if (showPlayerEquipment) {
            if (!useCustomEquipment) {
                sendEquipmentPacket(p, EquipmentSlot.HAND, getPlayer().getItemInHand());
                sendEquipmentPacket(p, EquipmentSlot.HEAD, getPlayer().getInventory().getHelmet());
                sendEquipmentPacket(p, EquipmentSlot.CHEST, getPlayer().getInventory().getChestplate());
                sendEquipmentPacket(p, EquipmentSlot.LEGS, getPlayer().getInventory().getLeggings());
                sendEquipmentPacket(p, EquipmentSlot.FEET, getPlayer().getInventory().getBoots());
            } else {
                sendEquipmentPacket(p, EquipmentSlot.HAND, equipment[0]);
                sendEquipmentPacket(p, EquipmentSlot.HEAD, equipment[4]);
                sendEquipmentPacket(p, EquipmentSlot.CHEST, equipment[3]);
                sendEquipmentPacket(p, EquipmentSlot.LEGS, equipment[2]);
                sendEquipmentPacket(p, EquipmentSlot.FEET, equipment[1]);
            }
        } else {
            if (remove) {
                sendEquipmentPacket(p, EquipmentSlot.HAND, null);
                sendEquipmentPacket(p, EquipmentSlot.HEAD, null);
                sendEquipmentPacket(p, EquipmentSlot.CHEST, null);
                sendEquipmentPacket(p, EquipmentSlot.LEGS, null);
                sendEquipmentPacket(p, EquipmentSlot.FEET, null);
            }

        }
    }

    private void sendEquipmentPacket(Player p, EquipmentSlot slot, ItemStack it) {
        int id = -getPlayer().getEntityId();
        DisguisePlugin.nms.sendPacket(p, DisguisePlugin.nms.buildEquipment(id, slot.ordinal(), it));
    }

    public void setEquipment(EquipmentSlot slot, ItemStack equipment) {
        useCustomEquipment = true;
        showPlayerEquipment = false;
        this.equipment[slot.ordinal()] = equipment;
        for (Player p : getSeeing()) {
            sendEquipmentPacket(p, slot, equipment);
        }
    }

    public void setShowPlayerEquipment(boolean showPlayerEquipment) {
        this.showPlayerEquipment = showPlayerEquipment;
        for (Player p : getSeeing()) {
            showEquipment(p, true);
        }
    }

    public boolean isShowingPlayerEquipment() {
        return showPlayerEquipment;
    }

    @Override
    public boolean processOpt(String opt) {
        if (opt.equalsIgnoreCase("showequipment")) {
            setShowPlayerEquipment(!isShowingPlayerEquipment());
            return true;
        }
        return super.processOpt(opt);
    }
}