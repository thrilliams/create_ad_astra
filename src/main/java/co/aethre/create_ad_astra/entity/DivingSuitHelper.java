package co.aethre.create_ad_astra.entity;

import co.aethre.create_ad_astra.config.CreateAdAstraConfig;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import com.simibubi.create.content.equipment.armor.NetheriteDivingHandler;
import earth.terrarium.adastra.api.systems.OxygenApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DivingSuitHelper {
    public static boolean hasDivingSuit(LivingEntity entity) {
        ItemStack helmet = DivingHelmetItem.getWornItem(entity);
        if (helmet.isEmpty()) return false;

        BacktankItem backtank = BacktankItem.getWornBy(entity);
        return backtank != null;
    }

    public static boolean hasWorkingDivingSuit(LivingEntity entity) {
        return hasDivingSuit(entity) && !BacktankUtil.getAllWithAir(entity).isEmpty();
    }

    public static boolean hasNetheriteDivingSuit(LivingEntity entity) {
        return entity.getCustomData().getBoolean(NetheriteDivingHandler.FIRE_IMMUNE_KEY);
    }

    public static boolean shouldConsumeOxygen(LivingEntity entity) {
        return !OxygenApi.API.hasOxygen(entity) &&
                switch (CreateAdAstraConfig.server().divingSuitOxygen.get()) {
                    case ALL -> hasWorkingDivingSuit(entity);
                    case NETHERITE -> hasNetheriteDivingSuit(entity) && hasWorkingDivingSuit(entity);
                    default -> false;
                };
    }

    public static boolean hasOxygenatingDivingSuit(LivingEntity entity) {
        return switch (CreateAdAstraConfig.server().divingSuitOxygen.get()) {
            case ALL -> hasDivingSuit(entity);
            case NETHERITE -> hasNetheriteDivingSuit(entity);
            default -> false;
        };
    }

    public static boolean hasFreezeResistantDivingSuit(LivingEntity entity) {
        return switch (CreateAdAstraConfig.server().divingSuitFreezeResistance.get()) {
            case ALL -> hasWorkingDivingSuit(entity);
            case NETHERITE -> hasNetheriteDivingSuit(entity) && hasWorkingDivingSuit(entity);
            default -> false;
        };
    }

    public static boolean hasHeatResistantDivingSuit(LivingEntity entity) {
        return switch (CreateAdAstraConfig.server().divingSuitHeatResistance.get()) {
            case ALL -> hasWorkingDivingSuit(entity);
            case NETHERITE -> hasNetheriteDivingSuit(entity) && hasWorkingDivingSuit(entity);
            default -> false;
        };
    }
}
