package com.github.bindernews.translucent;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {

	private HashMap<Player, Double> attackTimeMap = new HashMap<Player, Double>(50);
	
	@EventHandler(priority = EventPriority.LOW)
	public void cancelCommandsWhenFighting(PlayerCommandPreprocessEvent event)
	{
		if (!attackTimeMap.containsKey(event.getPlayer()))
			return;
		double time = millisToSeconds(System.currentTimeMillis());
		double lastTime = attackTimeMap.get(event.getPlayer());
		if (time - lastTime < Conf.timeToReenableCommands)
		{
			for(String s : Conf.commandsToDisableOnAttack)
			{
				if (event.getMessage().startsWith(s))
				{
					event.getPlayer().sendMessage(ChatColor.RED + "You may not use that command during combat!");
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void watchPlayerDamage(EntityDamageByEntityEvent event)
	{
		if ((event.getEntity() instanceof Player)
			&& (event.getDamager() instanceof Player)) 
		{
			attackTimeMap.put((Player)event.getEntity(), millisToSeconds(System.currentTimeMillis()));
		}
	}
	
	public static double millisToSeconds(long millis)
	{
		return ((double)millis)/1000.0;
	}
}
