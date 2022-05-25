package dev.u9g.mixin;

import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandomImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Xoroshiro128PlusPlusRandomImpl.class)
public interface XoroRandomImplMixin {
    @Accessor("seedLo")
    long seedLo();
    @Accessor("seedHi")
    long seedHi();
}