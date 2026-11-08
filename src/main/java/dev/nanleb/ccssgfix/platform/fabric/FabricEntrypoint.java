package dev.nanleb.cortescombatspatialguifix.platform.fabric;

//? fabric {

import dev.nanleb.cortescombatspatialguifix.cortescombatspatialguifix;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		cortescombatspatialguifix.onInitialize();
	}
}
//?}
