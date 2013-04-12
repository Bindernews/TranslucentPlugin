package com.github.bindernews.translucent;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.bindernews.translucent.mute.CmdMute;
import com.github.bindernews.translucent.mute.CmdMuteList;
import com.github.bindernews.translucent.mute.CmdUnmute;
import com.github.bindernews.translucent.mute.MuteHandler;
import com.github.bindernews.translucent.transcommand.CmdTransPlugin;

public class TransPlugin extends JavaPlugin {

	public static Logger logger;
	public static CmdTransPlugin cmdTransPlugin;
	public static TransPlugin instance;
	
	private MuteHandler muteHandler;
	private CmdMute cmdMute;
	private CmdUnmute cmdUnmute;
	private CmdMuteList cmdMuteList;
	private FactionsListener facListener;
	private PlayerListener playerListener;
	
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

		if (muteHandler == null)
		{
			// create instances
			cmdTransPlugin = new CmdTransPlugin();
			muteHandler = new MuteHandler();
			cmdMute = new CmdMute(muteHandler);
			cmdUnmute = new CmdUnmute(muteHandler);
			cmdMuteList = new CmdMuteList(muteHandler);
			facListener = new FactionsListener();
			playerListener = new PlayerListener();
		}
		
		muteHandler.loadValues();
		
		// register listeners
		getServer().getPluginManager().registerEvents(facListener, this);
		getServer().getPluginManager().registerEvents(playerListener, this);
		getServer().getPluginManager().registerEvents(muteHandler, this);
		
		// register commands
		getCommand("transplugin").setExecutor(cmdTransPlugin);
		getCommand("transplugin").setTabCompleter(cmdTransPlugin);
		getCommand("mute").setExecutor(cmdMute);
		getCommand("unmute").setExecutor(cmdUnmute);
		getCommand("mutelist").setExecutor(cmdMuteList);
		
		logger.info(getName() + " enabled");
	}
	
	@Override
	public void onDisable() {
		muteHandler.saveValues();
		logger.info(getName() + " disabled");
	}
	
	@Override
	public void onLoad() {
		// init logger
		logger = getLogger(); 
	}
}
