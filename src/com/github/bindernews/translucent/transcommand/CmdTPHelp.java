package com.github.bindernews.translucent.transcommand;

import org.bukkit.command.CommandSender;

import com.github.bindernews.bukkit.command.Subcommand;

public class CmdTPHelp extends Subcommand {

	public static final String
			NAME = "help",
			DESC = "Show help",
			USAGE = "help [page#]";
	
	
	public CmdTPHelp() {
		super(NAME, DESC, USAGE, null);
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		//int pageNum = 0;
		
		return false;
	}

}
