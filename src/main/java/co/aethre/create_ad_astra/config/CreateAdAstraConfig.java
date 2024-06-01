package co.aethre.create_ad_astra.config;

import co.aethre.create_ad_astra.CreateAdAstra;
import com.simibubi.create.foundation.config.ConfigBase;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class CreateAdAstraConfig {
    public static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

    private static Client client;
    private static Common common;
    private static Server server;

    public static Client client() {
        return client;
    }

    public static Common common() {
        return common;
    }

    public static Server server() {
        return server;
    }

    public static ConfigBase byType(ModConfig.Type type) {
        return CONFIGS.get(type);
    }

    private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
        Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            T config = factory.get();
            config.registerAll(builder);
            return config;
        });

        T config = specPair.getLeft();
        config.specification = specPair.getRight();
        CONFIGS.put(side, config);
        return config;
    }

    public static void register() {
        // client = register(Client::new, ModConfig.Type.CLIENT);
        // common = register(Common::new, ModConfig.Type.COMMON);
        server = register(Server::new, ModConfig.Type.SERVER);

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : CreateAdAstraConfig.CONFIGS.entrySet())
            ForgeConfigRegistry.INSTANCE.register(CreateAdAstra.ID, pair.getKey(), pair.getValue().specification);
        ModConfigEvents.loading(CreateAdAstra.ID).register(CreateAdAstraConfig::onLoad);
        ModConfigEvents.reloading(CreateAdAstra.ID).register(CreateAdAstraConfig::onReload);
    }

    public static void onLoad(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig.getSpec()) config.onLoad();
    }

    public static void onReload(ModConfig modConfig) {
        for (ConfigBase config : CONFIGS.values())
            if (config.specification == modConfig.getSpec()) config.onReload();
    }

    public static class Client extends ConfigBase {
        @Override
        public String getName() {
            return "client";
        }
    }

    public static class Common extends ConfigBase {
        @Override
        public String getName() {
            return "common";
        }
    }

    public static class Server extends ConfigBase {
        public final ConfigGroup divingSuits = group(1, "divingSuits", "Settings for Diving Suits");
        public final ConfigEnum<DivingSuitSetting> divingSuitOxygen = e(DivingSuitSetting.ALL, "oxygen", "Which will provide oxygen when the player has a filled Backtank");
        public final ConfigEnum<DivingSuitSetting> divingSuitFreezeResistance = e(DivingSuitSetting.ALL, "freezeResistance", "Which will provide resistance to extreme cold in space");
        public final ConfigEnum<DivingSuitSetting> divingSuitHeatResistance = e(DivingSuitSetting.NETHERITE, "heatResistance", "Which will provide resistance to extreme heat in space");

        public final ConfigGroup divingBoots = group(1, "divingBoots", "Settings for Diving Boots");
        public final ConfigEnum<GravitySetting> divingBootsGravityNormalization = e(GravitySetting.NON_ZERO, "gravityNormalization", "When to affect gravity");

        @Override
        public String getName() {
            return "server";
        }
    }
}