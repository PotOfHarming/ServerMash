package pot.potionofharming;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.MapColor;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pot.potionofharming.events.ModifyBlock;

public class ServerMash implements ModInitializer {
	public static final String MOD_ID = "servermash";
	public static MinecraftServer SERVER;
	public static boolean debug = false;

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Loading ServerMash...");
		ServerLifecycleEvents.SERVER_STARTED.register(sv ->  {
			SERVER = sv;
		});

		ModifyBlock.initDetectionBlocks();

		LOGGER.info("Successfully loaded ServerMash!");
	}
}