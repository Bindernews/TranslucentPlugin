package com.github.bindernews.translucent;

import org.bukkit.plugin.java.JavaPlugin;

public class TransPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new FactionsListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onLoad() {
		getConfig().addDefaults(ConfManager.DEFAULTS);
		saveConfig();
	}
}
