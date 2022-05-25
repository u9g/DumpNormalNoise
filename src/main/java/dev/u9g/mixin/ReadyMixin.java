package dev.u9g.mixin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.DumpNormalNoise;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.FileWriter;
import java.io.Writer;

@Mixin(MinecraftDedicatedServer.class)
public class ReadyMixin {
    @Inject(at = @At("TAIL"), method = "setupServer()Z")
    public void init(CallbackInfoReturnable<Boolean> cir) {
        try (Writer writer = new FileWriter("output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                JsonObject obj = new JsonObject();
//                obj.addProperty("seed", ((MinecraftServer)FabricLoader.getInstance().getGameInstance()).getOverworld().getSeed());
//                JsonArray noise = new JsonArray();
//                for (DoublePerlinNoiseSampler.NoiseParameters param : BuiltinRegistries.NOISE_PARAMETERS) {
//                    JsonObject noiseObj = new JsonObject();
//                    noiseObj.addProperty("firstOctave", param.firstOctave());
//                    JsonArray amplitude = new JsonArray();
//                    for (Double amp : param.amplitudes()) {
//                        amplitude.add(amp);
//                    }
//                    noiseObj.add("amplitude", amplitude);
//                    noise.add(noiseObj);
//                }
//                obj.add("noise", noise);
//                gson.toJson(obj, writer);
            gson.toJson(DumpNormalNoise.XORO, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
