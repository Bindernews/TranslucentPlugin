package com.github.bindernews.translucent.transcommand;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.bindernews.bukkit.command.Subcommand;
import com.github.bindernews.translucent.TransPlugin;

public class CmdTPReload extends Subcommand {

	public CmdTPReload() {
		super("reload", "Reload configuration", "", new ArrayList<String>());
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		TransPlugin.instance.reloadConfig();
		sender.sendMessage(ChatColor.GREEN + "Translucent Plugin config reloaded");
		return true;
	}

}
