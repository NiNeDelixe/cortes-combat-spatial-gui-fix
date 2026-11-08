"""Check the injection and both cancellation paths without launching Minecraft."""

from pathlib import Path
import subprocess
import tempfile


root = Path(__file__).resolve().parents[1]
source = (root / "src/main/java/dev/nanleb/ccssgfix/mixin/CombatCrosshairMixin.java").read_text()
source = source.replace("/*import", "import").replace("*///?}", "//?}")
assert '@Mixin(value = CombatCrosshairLayer.class, remap = false)' in source
assert '@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)' in source
assert '@Inject(method = "render", at = @At("HEAD"), cancellable = true)' in source

sources = {
    "dev/nanleb/cortescombatspatialguifix/mixin/CombatCrosshairMixin.java": source,
    "com/cortes/combat/client/CombatCrosshairLayer.java": """
        package com.cortes.combat.client;
        public class CombatCrosshairLayer {}
    """,
    "dev/kikugie/fletching_table/annotation/MixinEnvironment.java": """
        package dev.kikugie.fletching_table.annotation;
        public @interface MixinEnvironment {
            Env type();
            enum Env { CLIENT }
        }
    """,
    "org/spongepowered/asm/mixin/Mixin.java": """
        package org.spongepowered.asm.mixin;
        public @interface Mixin { Class<?> value(); boolean remap(); }
    """,
    "org/spongepowered/asm/mixin/injection/At.java": """
        package org.spongepowered.asm.mixin.injection;
        public @interface At { String value(); }
    """,
    "org/spongepowered/asm/mixin/injection/Inject.java": """
        package org.spongepowered.asm.mixin.injection;
        public @interface Inject { String method(); At at(); boolean cancellable(); }
    """,
    "org/spongepowered/asm/mixin/injection/callback/CallbackInfo.java": """
        package org.spongepowered.asm.mixin.injection.callback;
        public class CallbackInfo {
            public boolean cancelled;
            public void cancel() { cancelled = true; }
        }
    """,
    "org/tastytrash/spatialGUI/client/SpatialGUIClient.java": """
        package org.tastytrash.spatialGUI.client;
        public class SpatialGUIClient {
            public static boolean hidden;
            public static boolean shouldHideCrosshair() { return hidden; }
        }
    """,
    "CrosshairCheck.java": """
        import dev.nanleb.ccssgfix.mixin.CombatCrosshairMixin;
        import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
        import org.tastytrash.spatialGUI.client.SpatialGUIClient;
        public class CrosshairCheck {
            public static void main(String[] args) throws Exception {
                var method = CombatCrosshairMixin.class.getDeclaredMethod("hideCrosshair", CallbackInfo.class);
                method.setAccessible(true);
                for (boolean hidden : new boolean[] { false, true }) {
                    SpatialGUIClient.hidden = hidden;
                    var ci = new CallbackInfo();
                    method.invoke(new CombatCrosshairMixin(), ci);
                    assert ci.cancelled == hidden : "Unexpected crosshair cancellation";
                }
            }
        }
    """,
}

with tempfile.TemporaryDirectory() as directory:
    files = []
    for name, contents in sources.items():
        path = Path(directory) / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(contents)
        files.append(str(path))
    subprocess.run(["javac", "-d", directory, *files], check=True)
    subprocess.run(["java", "-ea", "-cp", directory, "CrosshairCheck"], check=True)

print("Crosshair mixin checks passed (visible and hidden).")
