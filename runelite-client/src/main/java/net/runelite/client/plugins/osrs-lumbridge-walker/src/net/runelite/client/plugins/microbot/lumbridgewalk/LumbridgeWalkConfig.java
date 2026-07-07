package net.runelite.client.plugins.microbot.lumbridgewalk;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(LumbridgeWalkConfig.CONFIG_GROUP)
public interface LumbridgeWalkConfig extends Config
{
	String CONFIG_GROUP = "micro-lumbridge-walk";

	@ConfigItem(
		keyName = "guide",
		name = "How to use",
		description = "Instructions for this script",
		position = 0
	)
	default String guide()
	{
		return "Stand in Lumbridge, enable the plugin, and the script walks to the Al-Kharid gate. "
			+ "Bring 10 coins unless you have completed Prince Ali Rescue.";
	}
}
