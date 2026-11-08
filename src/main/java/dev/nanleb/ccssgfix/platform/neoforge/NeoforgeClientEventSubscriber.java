package dev.nanleb.ccssgfix.platform.neoforge;

//? neoforge {

import dev.nanleb.ccssgfix.cortescombatspatialguifix;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = cortescombatspatialguifix.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {
	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		cortescombatspatialguifix.onInitializeClient();
	}
}
//?}
