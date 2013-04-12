package com.github.bindernews.translucent.mute;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdMuteList implements CommandExecutor {

	private MuteHandler handler;
	
	public CmdMuteList(MuteHandler handle) {
		handler = handle;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.LIGHT_PURPLE);
		for(String player : handler.getMutedPlayers()) {
			sb.append(player);
			sb.append("  ");
		}
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "Muted Players:");
		sender.sendMessage(sb.toString());
		return true;
	}

}
