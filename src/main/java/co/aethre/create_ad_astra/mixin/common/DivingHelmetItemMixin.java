package co.aethre.create_ad_astra.mixin.common;

import co.aethre.create_ad_astra.entity.DivingSuitHelper;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DivingHelmetItem.class)
abstract class DivingHelmetItemMixin {
    @Redirect(
            method = "breatheUnderwater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private static boolean isEyeInFluidOrWithoutOxygen(LivingEntity entity, TagKey<Fluid> fluidTag) {
        return entity.isEyeInFluid(fluidTag) || DivingSuitHelper.shouldConsumeOxygen(entity);
    }

    @ModifyVariable(
            method = "breatheUnderwater",
            ordinal = 2,
            at = @At(
                    value = "LOAD",
                    ordinal = 3
            )
    )
    private static boolean lavaDivingOrWithoutOxygen(boolean lavaDiving, LivingEntity entity) {
        return lavaDiving || DivingSuitHelper.shouldConsumeOxygen(entity);
    }
}
