package com.github.bindernews.translucent.transcommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.bindernews.bukkit.command.Subcommand;
import com.github.bindernews.translucent.Conf;
import com.github.bindernews.translucent.ConfManager;

public class CmdTPConfig extends Subcommand {

	public CmdTPConfig() {
		super("config", "View/change configuration values", "config <option> [newvalue]", new ArrayList<String>());
	}

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Too few arguments!");
			return true;
		}
		if (!Conf.has(args[0])) {
			sender.sendMessage("Unknown configuration option");
			return true;
		}
		if (args.length > 1) {
			if (!ConfManager.setFromString(args[0], args[1])) {
				sender.sendMessage("Invalid type format");
				sender.sendMessage("Type of '" + args[0] + "' is " + ConfManager.getOptionTypeName(args[0]));
				return true;
			}
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias,
			String[] args) {
		if (args.length == 1) {
			if (args[0].length() > 0) {
				ArrayList<String> opts = new ArrayList<String>();
				for(String name : ConfManager.DEFAULTS.keySet()) {
					if (name.startsWith(args[0]))
						opts.add(name);
				}
				return opts;
			}
			else {
				return new ArrayList<String>(ConfManager.DEFAULTS.keySet());
			}
		}
		else {
			return null;
		}
		
	}

}
