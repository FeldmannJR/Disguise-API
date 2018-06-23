package me.feldmannjr.disguise.listeners;

import com.mojang.authlib.GameProfile;
import me.feldmannjr.disguise.DisguiseAPI;
import me.feldmannjr.disguise.types.player.DisguisePlayer;
import me.feldmannjr.disguise.types.base.DisguiseData;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class PlayerPacketListener extends PacketHandler {

    @Override
    public void onSend(SentPacket sentPacket) {
        if (sentPacket.getPlayer() == null) return;
        if (sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutPlayerInfo")) {
            Enum m = (Enum) sentPacket.getPacketValue("a");
            String action = m.name();
            if (action.equalsIgnoreCase("ADD_PLAYER")) {
                List l = (List) sentPacket.getPacketValue("b");
                for (Object b: l) {
                    try {
                        Field d = b.getClass().getDeclaredField("d");
                        d.setAccessible(true);
                        GameProfile prof = (GameProfile) d.get(b);

                        DisguiseData data = DisguiseAPI.getDisguise(prof.getId());
                        if (data != null && data instanceof DisguisePlayer) {
                            d.set(b, ((DisguisePlayer) data).create(prof.getId() != sentPacket.getPlayer().getUniqueId()));
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutNamedEntitySpawn")) {
            UUID uid = (UUID) sentPacket.getPacketValue("b");
            DisguiseData data = DisguiseAPI.getDisguise(uid);
            if (data != null && data instanceof DisguisePlayer) {
                sentPacket.setPacketValue("b", ((DisguisePlayer) data).data.getUUID());
            }
        }

    }

    @Override
    public void onReceive(ReceivedPacket receivedPacket) {

    }
}
