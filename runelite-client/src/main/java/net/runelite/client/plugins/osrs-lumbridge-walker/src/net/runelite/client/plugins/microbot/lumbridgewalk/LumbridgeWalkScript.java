package net.runelite.client.plugins.microbot.lumbridgewalk;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.util.player.Rs2Player;
import net.runelite.client.plugins.microbot.util.walker.Rs2Walker;

import java.util.concurrent.TimeUnit;

/**
 * Walks from Lumbridge to the Al-Kharid toll gate using Microbot's web walker.
 */
@Slf4j
public class LumbridgeWalkScript extends Script
{
	/** Al-Kharid gate on the Lumbridge side (OSRS wiki: 3267, 3227). */
	private static final WorldPoint AL_KHARID_GATE = new WorldPoint(3267, 3227, 0);

	public boolean run()
	{
		mainScheduledFuture = scheduledExecutorService.schedule(() ->
		{
			try
			{
				// Wait until logged in and no blocking events are active
				while (!Thread.currentThread().isInterrupted())
				{
					if (!Microbot.isLoggedIn())
					{
						sleep(600);
						continue;
					}
					if (!super.run())
					{
						sleep(600);
						continue;
					}
					break;
				}

				if (Thread.currentThread().isInterrupted())
				{
					return;
				}

				WorldPoint start = Rs2Player.getWorldLocation();
				log.info("Starting walk from {} to Al-Kharid gate {}", start, AL_KHARID_GATE);
				Microbot.status = "Walking to Al-Kharid gate...";

				boolean arrived = Rs2Walker.walkTo(AL_KHARID_GATE, 3);

				if (arrived)
				{
					Microbot.status = "Arrived at Al-Kharid gate";
					log.info("Walk complete at {}", Rs2Player.getWorldLocation());
				}
				else
				{
					Microbot.status = "Walk stopped before reaching gate";
					log.warn("Walker did not report arrival at {}", AL_KHARID_GATE);
				}
			}
			catch (Exception ex)
			{
				log.error("Lumbridge walk script failed", ex);
				Microbot.status = "Script error — see logs";
			}
			finally
			{
				shutdown();
			}
		}, 0, TimeUnit.MILLISECONDS);

		return true;
	}
}
