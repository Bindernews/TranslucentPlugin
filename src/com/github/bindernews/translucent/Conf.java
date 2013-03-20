package com.github.bindernews.translucent;

import java.util.HashSet;
import java.util.Set;

public class Conf {

	public static double playerPowerNeedToAttack = 0;
	public static boolean disableLeaveWithNegativePower = false;
	
	public static Set<String> commandsToDisableOnAttack = new HashSet<String>();
	public static double timeToReenableCommands = 0.0;
	
}
