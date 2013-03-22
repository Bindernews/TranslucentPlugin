package com.github.bindernews.translucent;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.bindernews.translucent.transcommand.CmdTransPlugin;

public class TransPlugin extends JavaPlugin {

	public static Logger logger;
	
	public static CmdTransPlugin cmdTransPlugin;
	
	public static TransPlugin instance;
	
	public TransPlugin() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		
		// load config and add defaults if not present
		// Then, save the config again in case defaults were added
		boolean didChangeDiskConf = ConfManager.writeConfig(getConfig(), true);
		ConfManager.loadConfig(getConfig());
		if (didChangeDiskConf) {
			saveConfig();
		}

		// register listeners
		getServer().getPluginManager().registerEvents(new FactionsListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		/*
		// register commands
		cmdTransPlugin = new CmdTransPlugin();
		getCommand("transplugin").setExecutor(cmdTransPlugin);
		getCommand("transplugin").setTabCompleter(cmdTransPlugin);
		*/
		
		logger.info(getName() + "enabled");
	}
	
	@Override
	public void onDisable() {
		logger.info(getName() + "disabled");
	}
	
	@Override
	public void onLoad() {
		// init logger
		logger = getLogger(); 
	}
}
