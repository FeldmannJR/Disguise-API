package me.feldmannjr.disguise.cmds;

import me.feldmannjr.disguise.DisguiseAPI;
import me.feldmannjr.disguise.DisguiseTypes;
import me.feldmannjr.disguise.types.player.DisguisePlayer;
import me.feldmannjr.disguise.types.base.DisguiseData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDisguise implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (strings.length == 0) {
                if (DisguiseAPI.getDisguise(p.getUniqueId()) != null) {
                    DisguiseAPI.setDisguise(p, null);
                    p.sendMessage("Voltou ao normal!");
                    return false;
                }
            }
            if (strings.length == 1) {
                String a = strings[0];
                DisguiseData data = DisguiseAPI.getDisguise(p.getUniqueId());

                if (data != null && data.processOpt(a)) {
                    p.sendMessage("§aFeito");
                    return false;
                }
                String tipos = "";
                for (DisguiseTypes type: DisguiseTypes.values()) {
                    if (type.name().equalsIgnoreCase(strings[0])) {
                        DisguiseAPI.setDisguise(p, type.createData(p));
                        p.sendMessage("Transformado em " + type.name());
                        return false;
                    }
                    tipos += type.name().toLowerCase() + " ";
                }
                cs.sendMessage("Tipo não encontrado! Tipos:" + tipos);
            }
            if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("player")) {
                    String nome = strings[1];
                    DisguiseAPI.setDisguise(p, new DisguisePlayer(p, nome));
                }
            }
        }
        return false;
    }
}
