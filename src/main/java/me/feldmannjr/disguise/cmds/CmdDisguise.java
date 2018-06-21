package me.feldmannjr.disguise.cmds;

import me.feldmannjr.disguise.DisguiseAPI;
import me.feldmannjr.disguise.DisguiseData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CmdDisguise implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings)
    {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (DisguiseAPI.getDisguise(p.getUniqueId()) != null) {
                DisguiseAPI.setDisguise(p, null);
                p.sendMessage("null");
            } else {
                DisguiseAPI.setDisguise(p, new DisguiseData(p, EntityType.ZOMBIE));
                p.sendMessage("Zombie");
            }


        }
        return false;
    }
}
