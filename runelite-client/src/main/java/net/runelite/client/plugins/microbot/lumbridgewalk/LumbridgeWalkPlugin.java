package net.runelite.client.plugins.microbot.lumbridgewalk;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
	name = PluginDescriptor.Default + "Lumbridge Walk",
	description = "Walks from Lumbridge to the Al-Kharid gate",
	tags = {"microbot", "walker", "lumbridge", "al kharid"},
	enabledByDefault = false
)
@Slf4j
public class LumbridgeWalkPlugin extends Plugin
{
	@Inject
	private LumbridgeWalkConfig config;

	@Inject
	private LumbridgeWalkScript script;

	@Provides
	LumbridgeWalkConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LumbridgeWalkConfig.class);
	}

	@Override
	protected void startUp()
	{
		log.info("Lumbridge Walk plugin started");
		script.run();
	}

	@Override
	protected void shutDown()
	{
		script.shutdown();
		log.info("Lumbridge Walk plugin stopped");
	}
}
