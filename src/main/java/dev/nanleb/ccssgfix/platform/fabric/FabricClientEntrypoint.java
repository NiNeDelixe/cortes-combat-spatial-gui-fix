package dev.nanleb.cortescombatspatialguifix.platform.fabric;

//? fabric {

import dev.nanleb.cortescombatspatialguifix.cortescombatspatialguifix;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		cortescombatspatialguifix.onInitializeClient();
	}

}
//?}
