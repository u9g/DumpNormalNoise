package dev.u9g.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.DumpNormalNoise;
import net.minecraft.world.level.levelgen.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;

@Mixin(XoroshiroRandomSource.XoroshiroPositionalRandomFactory.class)
public class NoiseMixin {
    @Shadow @Final private long seedLo;

    @Shadow @Final private long seedHi;

    @Inject(at = @At("TAIL"), method = "fromHashOf(Ljava/lang/String;)Lnet/minecraft/world/level/levelgen/RandomSource;", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void fromHashOf(String string, CallbackInfoReturnable<RandomSource> cir, byte[] bs, long l, long m) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", string);
            obj.addProperty("seedLo", ((Long)this.seedLo).toString());
            obj.addProperty("seedHi", ((Long)this.seedHi).toString());
            obj.addProperty("l", ((Long)l).toString());
            obj.addProperty("m", ((Long)m).toString());
            StackTraceElement[] elems = new Throwable().getStackTrace();
            JsonArray stacktrace = new JsonArray();
            for (String elem : Arrays.stream(elems).map(e -> e.getClassName()+"#"+e.getMethodName()+":"+e.getLineNumber()).limit(10).toList()) {
                stacktrace.add(elem);
            }
            obj.add("stacktrace", stacktrace);
            DumpNormalNoise.XORO.add(obj);
    }
//    @Inject(at = @At("HEAD"), method = "instantiate(Lnet/minecraft/world/level/levelgen/PositionalRandomFactory;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/levelgen/synth/NormalNoise;")
//    private static NormalNoise instantiate(PositionalRandomFactory positionalRandomFactory, Holder<NormalNoise.NoiseParameters> holder, CallbackInfoReturnable<NormalNoise> cir) {
//        ResourceLocation location = holder.unwrapKey().orElseThrow().location();
//        if (positionalRandomFactory instanceof XoroshiroRandomSource.XoroshiroPositionalRandomFactory rd) {
//            Xoroshiro128PlusPlus impl = (((XoroRandomAccessor) rd.fromHashOf(location)).randomNumberGenerator());
//            long seedLo = ((XoroRandomImplMixin)impl).seedLo();
//            long seedHi = ((XoroRandomImplMixin)impl).seedHi();
//            JsonObject obj = new JsonObject();
//            obj.addProperty("name", location.toString());
//            obj.addProperty("seedLo", ((Long)seedLo).toString());
//            obj.addProperty("seedHi", ((Long)seedHi).toString());
//            StackTraceElement[] elems = new Throwable().getStackTrace();
//            JsonArray stacktrace = new JsonArray();
//            for (String elem : Arrays.stream(elems).map(e -> e.getClassName()+"#"+e.getMethodName()+":"+e.getLineNumber()).toList()) {
//                stacktrace.add(elem);
//            }
//            obj.add("stacktrace", stacktrace);
//            DumpNormalNoise.XORO.add(obj);
////            System.out.println(location+" {\n  seedlo: "+seedLo+"\n  seedhi: "+seedHi+"\n}");
//        }
//    }
}
