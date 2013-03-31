package com.github.bindernews.translucent.transcommand;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.github.bindernews.bukkit.command.Subcommand;
import com.github.bindernews.bukkit.command.SubcommandMap;

public class CmdTransPlugin implements CommandExecutor, TabCompleter {

	private SubcommandMap cmdMap;
	private Subcommand
			cTPConfig,
			cTPReload;
	
	public CmdTransPlugin() {
		cmdMap = new SubcommandMap();
		cTPConfig = new CmdTPConfig();
		cTPReload = new CmdTPReload();
		cTPConfig.register(cmdMap);
		cTPReload.register(cmdMap);
		cmdMap.printMapping();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String alias, String[] args) {
		try
		{
			if (args.length == 0) {
				sender.sendMessage("Do '/transplugin help' for help");
				return true;
			} else {
				return cmdMap.dispatch(sender, args);
			}
		}
		catch (CommandException ex)
		{
			sender.sendMessage(ex.getMessage());
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		try
		{
			return cmdMap.tabComplete(sender, args);
		}
		catch (IllegalArgumentException iae)
		{
			sender.sendMessage("Error: Unknown subcommand");
			return null;
		}
	}
	
	public void displayHelp(CommandSender sender, int page) {
		
	}

}
