package me.feldmannjr.disguise.cmds;

import me.feldmannjr.disguise.DisguiseAPI;
import me.feldmannjr.disguise.types.DisguiseCreeper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDisguise implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player) {
            if (strings.length == 1) {
                DisguiseCreeper disguise = (DisguiseCreeper) DisguiseAPI.getDisguise(((Player) cs).getUniqueId());
                if (strings[0].equals("fuse")) {
                    disguise.setFuse(!disguise.isFuse());
                } else {
                    disguise.setPowered(!disguise.isPowered());
                }
                return false;
            }
            Player p = (Player) cs;
            if (DisguiseAPI.getDisguise(p.getUniqueId()) != null) {
                DisguiseAPI.setDisguise(p, null);
                p.sendMessage("null");
            } else {
                DisguiseAPI.setDisguise(p, new DisguiseCreeper(p));
                p.sendMessage("Zombie");
            }

        }
        return false;
    }
}
