package me.vaqxine.Hive.commands;

import me.vaqxine.Hive.Hive;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSync implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player)sender;
        p.updateInventory();
        p.teleport(p);

        if((System.currentTimeMillis() - Hive.login_time.get(p.getName())) <= 5000){
            int seconds_left = 5 - (int)((System.currentTimeMillis() - Hive.login_time.get(p.getName())) / 1000.0D);
            p.sendMessage(ChatColor.RED + "You cannot /sync for another " + seconds_left + ChatColor.BOLD + "s");
            return true;
        }

        if(Hive.last_sync.containsKey(p.getName()) && (((System.currentTimeMillis() - Hive.last_sync.get(p.getName())) <= 10000))){
            p.sendMessage(ChatColor.RED + "You already have a recent sync request -- please wait a few seconds.");
            return true;
        }
        Hive.last_sync.put(p.getName(), System.currentTimeMillis());

        //sync_queue.add(p.getName()); - Disabled lol
        p.sendMessage(ChatColor.GREEN + "Synced player data to " + ChatColor.UNDERLINE + "HIVE" + ChatColor.GREEN + " server.");

        return true;
	}
	
}