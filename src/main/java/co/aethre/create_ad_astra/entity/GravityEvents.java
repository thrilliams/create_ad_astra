package co.aethre.create_ad_astra.entity;

import co.aethre.create_ad_astra.config.CreateAdAstraConfig;
import com.simibubi.create.content.equipment.armor.DivingBootsItem;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GravityEvents {
    private static boolean gravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        return switch (CreateAdAstraConfig.server().divingBootsGravityNormalization.get()) {
            case ALWAYS, NON_ZERO -> !DivingBootsItem.isWornBy(entity);
            case NEVER -> true;
        };
    }

    private static boolean zeroGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        return switch (CreateAdAstraConfig.server().divingBootsGravityNormalization.get()) {
            case ALWAYS -> !DivingBootsItem.isWornBy(entity);
            case NON_ZERO, NEVER -> true;
        };
    }

    public static void register() {
        AdAstraEvents.GravityTickEvent.register(GravityEvents::gravityTick);
        AdAstraEvents.ZeroGravityTickEvent.register(GravityEvents::zeroGravityTick);
    }
}
