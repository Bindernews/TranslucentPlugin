package com.github.bindernews.bukkit.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class Subcommand extends Command {
	
	protected Subcommand(String name) {
		super(name);
	}

	protected Subcommand(String name, String description, String usageMessage,
			List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}

	@Override
	public abstract boolean execute(CommandSender sender, String alias, String[] args);

	//@Override
	//public abstract List<String> tabComplete(CommandSender sender, String alias, String[] args);

	public boolean register(SubcommandMap cmap)
	{
		return cmap.register(getLabel(), "", this);
	}

}
