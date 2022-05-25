package dev.u9g.mixin;

import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandom;
import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandomImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Xoroshiro128PlusPlusRandom.class)
public interface XoroRandomAccessor {
    @Accessor("implementation")
    Xoroshiro128PlusPlusRandomImpl implementation();
}
