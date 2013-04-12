package com.github.bindernews.translucent.mute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdMute implements CommandExecutor {
	
	private MuteHandler handler;
	
	public CmdMute(MuteHandler handle) {
		handler = handle;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "You must specify a player");
			return true;
		}
		handler.addPlayer(args[0]);
		String muteMessage = ChatColor.YELLOW + args[0] + ChatColor.RED + " has been muted";
		if (args.length > 1) {
			muteMessage += " for " + args[1];
		}
		Bukkit.broadcastMessage(muteMessage);
		handler.saveValues();
		return true;
	}

}
