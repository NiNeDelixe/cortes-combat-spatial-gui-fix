package dev.nanleb.ccssgfix;

import dev.nanleb.ccssgfix.platform.Platform;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
//import dev.nanleb.ccssgfix.platform.fabric.FabricPlatform;
//?} neoforge {
import dev.nanleb.ccssgfix.platform.neoforge.NeoforgePlatform;
 //?} forge {
//import dev.nanleb.ccssgfix.platform.forge.ForgePlatform;
 //?}

@SuppressWarnings("LoggingSimilarMessage")
public class cortescombatspatialguifix {

	public static final String MOD_ID = /*$ mod_id*/"cortescombatspatialguifix";
	public static final String MOD_VERSION = /*$ mod_version*/"0.1.0";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/"Cortes combat spatial gui fix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		LOGGER.info("Initializing {} on {}", MOD_ID, cortescombatspatialguifix.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, cortescombatspatialguifix.xplat().loader());
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		//return new FabricPlatform();
		//?} neoforge {
		return new NeoforgePlatform();
		 //?} forge {
		//return new ForgePlatform();
		 //?}
	}

	public static ResourceLocation id(String path) {
		//? > 1.19.2 {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
		 //?} <= 1.19.2 {
		//return new ResourceLocation(MOD_ID, path);
		//?}
	}

	public static ResourceLocation id(String namespace, String path) {
		//? > 1.19.2 {
		return ResourceLocation.fromNamespaceAndPath(namespace, path);
		 //?} <= 1.19.2 {
		//return new ResourceLocation(namespace, path);
		//?}
	}
}
