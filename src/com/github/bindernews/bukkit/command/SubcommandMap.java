package com.github.bindernews.bukkit.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import com.github.bindernews.translucent.ConfManager;

public class SubcommandMap implements CommandMap {

	private Map<String, Command> cmdMap;
	
	public SubcommandMap() {
		cmdMap = new HashMap<String, Command>(10);
	}
	
	@Override
	public void clearCommands() {
		cmdMap.clear();
	}

	@Override
	public boolean dispatch(CommandSender sender, String commandLine)
			throws CommandException {
		String[] splitCmd = splitCommandLine(commandLine);
		return dispatch(sender, splitCmd);
	}
	
	public boolean dispatch(CommandSender sender, String[] splitCmd)
			throws CommandException {
		String cmdName = splitCmd[0];
		if (cmdName.startsWith("/")) {
			cmdName = cmdName.substring(1);
		}
		Command cmd = getCommand(cmdName);
		if (cmd == null) {
			throw new CommandException("Command \"" + cmdName + "\" not found");
		}
		String[] args = Arrays.copyOfRange(splitCmd, 1, splitCmd.length);
		return cmd.execute(sender, cmdName, args);
	}

	@Override
	public Command getCommand(String name) {
		return cmdMap.get(name);
	}

	@Override
	public boolean register(String fallbackPrefix, Command command) {
		return register(command.getLabel(), fallbackPrefix, command);
	}

	@Override
	public boolean register(String label, String fallbackPrefix, Command command) {
		String cmdName = label;
		boolean usedFallback = false;
		
		if (cmdMap.containsKey(cmdName)) {
			do
			{
				cmdName = ":" + cmdName;
			}
			while(cmdMap.containsKey(fallbackPrefix + cmdName));
			cmdName = fallbackPrefix + cmdName;
			usedFallback = true;
		}
		cmdMap.put(cmdName, command);
		return usedFallback;
	}

	@Override
	public void registerAll(String fallbackPrefix, List<Command> commands) {
		for(Command cmd : commands) {
			register(fallbackPrefix, cmd);
		}
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String commandLine)
			throws IllegalArgumentException {
		String[] splitCmd = splitCommandLine(commandLine);
		return tabComplete(sender, splitCmd);
	}
	
	// advanced tab-completion
	public List<String> tabComplete(CommandSender sender, String[] splitCmd)
			throws IllegalArgumentException {
		
		// this will return a list of the sub commands
		if (splitCmd.length == 0 || (splitCmd.length == 1 && splitCmd[0].length() == 0)) {
			return new ArrayList<String>(cmdMap.keySet());
		}
		
		// extract command name
		String cmdName = splitCmd[0];
		if (cmdName.startsWith("/")) {
			cmdName = cmdName.substring(1);
		}
		
		// if a command is partially typed then do completion with that name 
		if (!cmdMap.containsKey(cmdName) && splitCmd.length == 1) {
			ArrayList<String> opts = new ArrayList<String>();
			for(String name : ConfManager.DEFAULTS.keySet()) {
				if (name.startsWith(splitCmd[0]))
					opts.add(name);
			}
			return opts;
		}
		else {
			Command cmd = getCommand(cmdName);
			if (cmd == null) {
				throw new IllegalArgumentException("Command \"" + cmdName + "\" not found");
			}
			String[] args = Arrays.copyOfRange(splitCmd, 1, splitCmd.length);
			return cmd.tabComplete(sender, cmdName, args);
		}
	}
	
	public String[] splitCommandLine(String cmdLine) {
		return cmdLine.split(" ");
	}
	
	public void printMapping() {
		Logger log = Bukkit.getServer().getLogger();
		for(Entry<String, Command> ent : cmdMap.entrySet()) {
			log.info(ent.getKey() + " : " + ent.getValue());
		}
	}

}
