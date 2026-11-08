package dev.nanleb.ccssgfix.mixin;

//? if (neoforge && 1.21.1) || (forge && 1.20.1) {
import com.cortes.combat.client.CombatCrosshairLayer;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tastytrash.spatialGUI.api.SpatialGUIAPI;

@Mixin(value = CombatCrosshairLayer.class, remap = false)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public class CombatCrosshairMixin {

	// Callback-only injection supports both Forge and NeoForge render signatures.
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void hideCrosshair(CallbackInfo ci) {
		if (SpatialGUIAPI.isActive()) {
			ci.cancel();
		}
	}
}
//?}
