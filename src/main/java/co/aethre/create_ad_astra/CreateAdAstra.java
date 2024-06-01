package co.aethre.create_ad_astra;

import co.aethre.create_ad_astra.config.CreateAdAstraConfig;
import co.aethre.create_ad_astra.entity.GravityEvents;
import co.aethre.create_ad_astra.entity.OxygenEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAdAstra implements ModInitializer {
    public static final String ID = "create_ad_astra";
    public static final String NAME = "Create + Ad Astra";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public void onInitialize() {
        CreateAdAstraConfig.register();
        OxygenEvents.register();
        GravityEvents.register();
    }
}
