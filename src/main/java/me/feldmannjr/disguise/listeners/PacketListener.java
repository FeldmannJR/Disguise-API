package me.feldmannjr.disguise.listeners;

import me.feldmannjr.disguise.DisguiseAPI;
import me.feldmannjr.disguise.DisguiseData;

import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import java.util.UUID;

public class PacketListener extends PacketHandler {

    public void onSend(SentPacket sentPacket)
    {

        if (sentPacket.getPlayer() == null) {
            return;
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutNamedEntitySpawn")) {
            handleSpawn(sentPacket);
        }

    }

    public void onReceive(ReceivedPacket receivedPacket)
    {

    }

    public void handleSpawn(SentPacket pa)
    {

        UUID uid = (UUID) pa.getPacketValue("b");
        DisguiseData data = DisguiseAPI.getDisguise(uid);
        if (data != null) {
            pa.setCancelled(true);
            data.sendSpawn(pa.getPlayer());
        }


    }


}
