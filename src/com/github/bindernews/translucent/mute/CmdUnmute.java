package com.github.bindernews.translucent.mute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdUnmute implements CommandExecutor {
	
	private MuteHandler handler;
	
	public CmdUnmute(MuteHandler handle) {
		handler = handle;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "You must specify a player");
			return true;
		}
		handler.removePlayer(args[0]);
		String muteMessage = ChatColor.YELLOW + args[0] + ChatColor.GREEN + " has been ummuted";
		Bukkit.broadcastMessage(muteMessage);
		handler.saveValues();
		return true;
	}

}
