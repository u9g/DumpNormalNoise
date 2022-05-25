package dev.u9g.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.DumpNormalNoise;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.random.RandomDeriver;
import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandom;
import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandomImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mixin(NoiseParametersKeys.class)
public class NoiseMixin {
    @Inject(at = @At("HEAD"), method = "method_41127(Lnet/minecraft/world/gen/random/RandomDeriver;Lnet/minecraft/util/registry/RegistryEntry;)Lnet/minecraft/util/math/noise/DoublePerlinNoiseSampler;")
    private static void method_41127(RandomDeriver randomDeriver, RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> registryEntry, CallbackInfoReturnable<DoublePerlinNoiseSampler> cir) {
        Identifier location = registryEntry.getKey().orElseThrow().getValue();
        if (randomDeriver instanceof Xoroshiro128PlusPlusRandom.RandomDeriver rd) {
            Xoroshiro128PlusPlusRandomImpl impl = (((XoroRandomAccessor) rd.createRandom(location)).implementation());
            long seedLo = ((XoroRandomImplMixin)impl).seedLo();
            long seedHi = ((XoroRandomImplMixin)impl).seedHi();
            JsonObject obj = new JsonObject();
            obj.addProperty("name", location.toString());
            obj.addProperty("seedLo", ((Long)seedLo).toString());
            obj.addProperty("seedHi", ((Long)seedHi).toString());
            StackTraceElement[] elems = new Throwable().getStackTrace();
            JsonArray stacktrace = new JsonArray();
            for (String elem : Arrays.stream(elems).map(e -> e.getClassName()+"#"+e.getMethodName()+":"+e.getLineNumber()).toList()) {
                stacktrace.add(elem);
            }
            obj.add("stacktrace", stacktrace);
            DumpNormalNoise.XORO.add(obj);
//            System.out.println(location+" {\n  seedlo: "+seedLo+"\n  seedhi: "+seedHi+"\n}");
        }
    }
}
