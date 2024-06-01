package co.aethre.create_ad_astra.mixin.common;

import co.aethre.create_ad_astra.entity.DivingSuitHelper;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.systems.OxygenApiImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OxygenApiImpl.class)
abstract class OxygenApiImplMixin {
    @Redirect(
            method = "entityTick",
            at = @At(
                    value = "INVOKE",
                    target = "Learth/terrarium/adastra/common/items/armor/SpaceSuitItem;hasFullSet(Lnet/minecraft/world/entity/LivingEntity;)Z"
            )
    )
    private boolean create_ad_astra$hasFullSet(LivingEntity entity) {
        return DivingSuitHelper.hasOxygenatingDivingSuit(entity) || SpaceSuitItem.hasFullSet(entity);
    }

    @Redirect(
            method = "entityTick",
            at = @At(
                    value = "INVOKE",
                    target = "Learth/terrarium/adastra/common/items/armor/SpaceSuitItem;hasOxygen(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean create_ad_astra$hasOxygen(Entity entity) {
        return ((entity instanceof LivingEntity livingEntity) && DivingSuitHelper.hasWorkingDivingSuit(livingEntity))
                || SpaceSuitItem.hasOxygen(entity);
    }
}
