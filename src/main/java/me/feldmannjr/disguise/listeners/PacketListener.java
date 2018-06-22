package me.feldmannjr.disguise.listeners;

import me.feldmannjr.disguise.DisguiseAPI;
import me.feldmannjr.disguise.DisguisePlugin;
import me.feldmannjr.disguise.types.base.DisguiseData;
import me.feldmannjr.disguise.types.base.EquipmentData;
import me.feldmannjr.disguise.types.base.LivingData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import java.util.UUID;

public class PacketListener extends PacketHandler {

    public void onSend(SentPacket sentPacket) {

        if (sentPacket.getPlayer() == null) {
            return;
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutNamedEntitySpawn")) {
            handleSpawn(sentPacket);
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutEntityEquipment")) {
            handleEquipment(sentPacket);
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutEntityDestroy")) {
            handleDestroi(sentPacket);
        }

    }

    public void onReceive(ReceivedPacket receivedPacket) {

    }

    public void handleEquipment(SentPacket pa) {
        int id = (Integer) pa.getPacketValue("a");
        if (id < 0) {
            pa.setPacketValue("a", -id);
            return;
        }
        Entity e = DisguisePlugin.getEntityById(pa.getPlayer().getWorld(), id);
        if (e instanceof Player) {
            DisguiseData data = DisguiseAPI.getDisguise(e.getUniqueId());
            if (data != null) {
                if (data instanceof EquipmentData) {
                    if (!((EquipmentData) data).isShowingPlayerEquipment()) {
                        pa.setCancelled(true);
                    }
                } else {
                    pa.setCancelled(true);
                }
            }
        }
    }

    public void handleDestroi(SentPacket pa) {

        for (int id : (int[]) pa.getPacketValue("a")) {
            Entity e = DisguisePlugin.getEntityById(pa.getPlayer().getWorld(), id);
            if (e instanceof Player) {
                DisguiseData data = DisguiseAPI.getDisguise(e.getUniqueId());
                if (data != null && data instanceof LivingData) {
                    ((LivingData) data).destroyArmorStand(pa.getPlayer());
                }
            }

        }
    }

    public void handleSpawn(SentPacket pa) {

        UUID uid = (UUID) pa.getPacketValue("b");
        DisguiseData data = DisguiseAPI.getDisguise(uid);
        if (data != null) {
            pa.setCancelled(true);
            data.sendSpawn(pa.getPlayer());
        }

    }

}
