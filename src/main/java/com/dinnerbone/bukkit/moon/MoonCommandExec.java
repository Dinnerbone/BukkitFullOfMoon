package com.dinnerbone.bukkit.moon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoonCommandExec implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Must be a player to teleport to world
		if (sender instanceof Player) {
			Player player = (Player)sender;
			player.teleport(BukkitMoon.getMoon().getSpawnLocation());
		} else {
			sender.sendMessage("You must be a player to execute this command!");
		}
		return true;
	}
}
