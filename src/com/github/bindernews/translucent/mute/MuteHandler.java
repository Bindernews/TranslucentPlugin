package com.github.bindernews.translucent.mute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.bindernews.translucent.TransPlugin;

public class MuteHandler implements Listener {

	public static final String[] DEFAULT_MUTED_MESSAGES = {
		"SILENCE CAPTIVE!",
		"LA LA LA LA I CAN'T HEAR YOU LA LA LA LA",
		"Ho ho ho! You've been muted!",
		"Little goblins have taken your voice away, and they're never gonna' give you up!"
	};
	
	private static final String LIST_NAME = "mutedPlayers"; 
	private Set<String> mutedPlayers;
	private String muteConfFile = TransPlugin.instance.getDataFolder().getPath() + "/mutes.yml";
	private YamlConfiguration muteCfg = new YamlConfiguration();
	private List<String> mutedMessages;
	private boolean showMutedMessages = false;
	
	
	public MuteHandler() {
		mutedPlayers = new HashSet<String>();
		mutedMessages = new ArrayList<String>(0);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		if (mutedPlayers.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
			event.setMessage("");
			if (showMutedMessages) {
				int rand = (int)(Math.random() * mutedMessages.size());
				event.getPlayer().sendMessage(ChatColor.RED + mutedMessages.get(rand));
			} else {
				event.getPlayer().sendMessage(ChatColor.RED + "You are muted!");
			}
		}
	}
	
	
	public void loadValues() {
		try {
			muteCfg.load(muteConfFile);
		} catch (FileNotFoundException e) {
			writeDefaults(false);
		} catch (IOException e) {
			writeDefaults(false);
		} catch (InvalidConfigurationException e) {
			writeDefaults(false);
		}
		//putDefaults(false);
		mutedPlayers.addAll( muteCfg.getStringList(LIST_NAME) );
		mutedMessages = muteCfg.getStringList("mutedMessages");
		showMutedMessages = muteCfg.getBoolean("showFunnyMutedMessages");
	}
	
	public void saveValues() {
		
		try {
			muteCfg.load(muteConfFile);
			muteCfg.set(LIST_NAME, new ArrayList<String>(mutedPlayers));
			muteCfg.save(muteConfFile);
		} catch (IOException e) {
			//TODO handle error
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void putDefaults(boolean overwrite) {
		if (overwrite) {
			muteCfg.set(LIST_NAME, new ArrayList<String>(1));
			muteCfg.set("mutedMessages", DEFAULT_MUTED_MESSAGES);
			muteCfg.set("showFunnyMutedMessages", false);
		} else {
			setIfEmpty(muteCfg, LIST_NAME, new ArrayList<String>(1));
			setIfEmpty(muteCfg, "mutedMessages", DEFAULT_MUTED_MESSAGES);
			setIfEmpty(muteCfg, "showFunnyMutedMessages", false);
		}
	}
	
	public void writeDefaults(boolean overwrite) {
		putDefaults(overwrite);
		saveValues();
	}
	
	private static boolean setIfEmpty(YamlConfiguration conf, String path, Object value) {
		if (!conf.contains(path)) {
			conf.set(path, value);
			return true;
		}
		return false;
	}
	
	public void addPlayer(String name) {
		mutedPlayers.add(name);
		Player player = Bukkit.getServer().getPlayerExact(name);
		player.sendMessage(ChatColor.RED + "You have been muted!");
	}
	
	public void removePlayer(String name) {
		mutedPlayers.remove(name);
		Player player = Bukkit.getServer().getPlayerExact(name);
		player.sendMessage(ChatColor.GREEN + "You have been unmuted!");
	}
	
	public Iterable<String> getMutedPlayers() {
		return mutedPlayers;
	}
}
