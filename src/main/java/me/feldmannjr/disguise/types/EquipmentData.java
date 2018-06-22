package me.feldmannjr.disguise.types;

import me.feldmannjr.disguise.DisguisePlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EquipmentData extends LivingData {

    boolean showPlayerEquipment = true;
    boolean useCustomEquipment = false;
    ItemStack[] equipment = new ItemStack[5];

    public EquipmentData(Player p, EntityType type) {
        super(p, type);
    }

    @Override
    public void sendSpawn(Player p) {
        super.sendSpawn(p);
        showEquipment(p);
    }

    public void showEquipment(Player p) {
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

    public boolean isShowingPlayerEquipment() {
        return showPlayerEquipment;
    }
}