package dev.u9g.mixin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.u9g.DumpNormalNoise;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.FileWriter;
import java.io.Writer;

@Mixin(DedicatedServer.class)
public class ReadyMixin {
    @Inject(at = @At("TAIL"), method = "initServer()Z")
    public void init(CallbackInfoReturnable<Boolean> cir) {
        try (Writer writer = new FileWriter("output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(DumpNormalNoise.XORO, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
