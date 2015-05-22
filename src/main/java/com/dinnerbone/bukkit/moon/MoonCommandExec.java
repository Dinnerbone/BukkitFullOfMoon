package com.dinnerbone.bukkit.moon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoonCommandExec implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.teleport(BukkitMoon.getMoon().getSpawnLocation());
		} else {
			sender.sendMessage("I don't know who you are!");
		}
		
		return true;
	}
}
