package com.github.bindernews.translucent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.Configuration;

public class ConfManager {
	
	
	public static final HashMap<String, Object> DEFAULTS =
			new HashMap<String, Object>();
	
	static 
	{
		for(Field f : Conf.class.getDeclaredFields())
		{
			try {
				DEFAULTS.put(f.getName(), f.get(null));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean loadConfig(Configuration conf)
	{
		for(Field f : Conf.class.getDeclaredFields())
		{
			try {
				if (f.getType() == Double.class)
				{
					f.set(null, conf.getDouble(f.getName(), f.getDouble(null)));
				}
				else if (f.getType() == Integer.class)
				{
					f.set(null, conf.getInt(f.getName(), f.getInt(null)));
				}
				else if (f.getType() == Boolean.class)
				{
					f.set(null, conf.getBoolean(f.getName(), f.getBoolean(null)));
				}
				else if (f.getType() == String.class)
				{
					f.set(null, conf.getString(f.getName(), (String)f.get(null)));
				}
				else if (f.getType() == List.class)
				{
					f.set(null, conf.getStringList(f.getName()));
				}
				else if (f.getType() == Set.class)
				{
					HashSet<String> hset = new HashSet<String>();
					hset.addAll( conf.getStringList(f.getName()) );
					f.set(null, hset);
				}
				else
				{
					throw new ClassCastException("Wrong type");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static boolean setField(Field f, String value) throws IllegalArgumentException
	{
		try {
			if (f.getType() == Double.class)
			{
				f.set(null, Double.parseDouble(value));
			}
			else if (f.getType() == Integer.class)
			{
				f.set(null, Integer.parseInt(value));
			}
			else if (f.getType() == Boolean.class)
			{
				f.set(null, Boolean.parseBoolean(value));
			}
			else if (f.getType() == String.class)
			{
				f.set(null, value);
			}
			return true;
		} catch (IllegalArgumentException | IllegalAccessException
				| SecurityException e) {
			return false;
		}
	}
}
