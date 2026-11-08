package dev.nanleb.ccssgfix.platform.neoforge;

//? neoforge {

import dev.nanleb.ccssgfix.cortescombatspatialguifix;
import net.neoforged.fml.common.Mod;

@Mod(cortescombatspatialguifix.MOD_ID)
public class NeoforgeEntrypoint {

	public NeoforgeEntrypoint() {
		cortescombatspatialguifix.onInitialize();
	}
}
//?}
