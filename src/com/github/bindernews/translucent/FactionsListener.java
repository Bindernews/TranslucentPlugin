package com.github.bindernews.translucent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FPlayerLeaveEvent;

public class FactionsListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event)
	{
		if (event.isCancelled())
			return;
		
		Entity entity = event.getEntity();
		if (! ((entity instanceof Player) && (event.getDamager() instanceof Player)) )
			return;
		
		FPlayer damager = FPlayers.i.get((Player)event.getDamager());
		if (damager.getPower() < Conf.playerPowerNeedToAttack + com.massivecraft.factions.Conf.powerPlayerMin)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLeaveFaction(FPlayerLeaveEvent event)
	{
		if (!Conf.disableLeaveWithNegativePower)
			return;
		if (event.getFaction().getPower() < 0)
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void preventExplosionsInOfflineFaction(EntityExplodeEvent event)
	{
		Faction fac = Board.getFactionAt(new FLocation(event.getLocation()));
		if (fac == null)
			return;
		if (!fac.hasPlayersOnline())
			event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void preventExplosionDamageInOfflineFaction(EntityDamageByEntityEvent event)
	{
		if (event.getCause() == DamageCause.BLOCK_EXPLOSION
				|| event.getCause() == DamageCause.ENTITY_EXPLOSION)
		{
			Faction fac = Board.getFactionAt(new FLocation(event.getDamager().getLocation()));
			if (fac == null || fac.isNone())
				return;
			if (!fac.hasPlayersOnline())
			{
				event.setCancelled(true);
				return;
			}
		}
	}
}
