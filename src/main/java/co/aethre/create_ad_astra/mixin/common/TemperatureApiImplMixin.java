package co.aethre.create_ad_astra.mixin.common;

import co.aethre.create_ad_astra.entity.DivingSuitHelper;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.systems.TemperatureApiImpl;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TemperatureApiImpl.class)
abstract class TemperatureApiImplMixin {
    @Redirect(
            method = "entityTick",
            at = @At(
                    value = "INVOKE",
                    target = "Learth/terrarium/adastra/common/items/armor/SpaceSuitItem;hasFullSet(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private boolean create_ad_astra$hasFullSet(LivingEntity entity, TagKey<Item> spaceSuitTag) {
        // all valid diving suits count for freeze resistance
        if (spaceSuitTag == ModItemTags.FREEZE_RESISTANT_ARMOR && DivingSuitHelper.hasFreezeResistantDivingSuit(entity))
            return true;
        // only netherite diving suits count for heat resistance
        if (spaceSuitTag == ModItemTags.HEAT_RESISTANT_ARMOR && DivingSuitHelper.hasHeatResistantDivingSuit(entity))
            return true;
        // if neither case is met, default to stock behavior
        return SpaceSuitItem.hasFullSet(entity, spaceSuitTag);
    }
}
