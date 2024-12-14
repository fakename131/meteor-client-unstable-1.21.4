/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.mixin;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.world.Ambience;
import net.minecraft.world.biome.FoliageColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoliageColors.class)
public abstract class FoliageColorsMixin {

    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
    private static void onGetBirchColor(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(getModifiedColor(info.getReturnValue()));
    }

    @Unique
    private static int getModifiedColor(int original) {
        if (Modules.get() == null) return original;

        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.customFoliageColor.get()) {
            return ambience.foliageColor.get().getPacked();
        }

        return original;
    }
}
