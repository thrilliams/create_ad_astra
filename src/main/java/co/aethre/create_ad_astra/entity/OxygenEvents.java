package co.aethre.create_ad_astra.entity;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.systems.PlanetData;
import earth.terrarium.adastra.client.utils.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class OxygenEvents {
    private static boolean playerOxygenListener(Entity entity, boolean hasOxygen) {
        Minecraft instance = Minecraft.getInstance();
        if (instance.level == null ||
                !instance.level.isClientSide ||
                instance.player == null ||
                !entity.is(instance.player))
            return hasOxygen;

        PlanetData localData = ClientData.getLocalData();
        if (localData == null) return hasOxygen;

        return localData.oxygen();
    }

    public static void register() {
        AdAstraEvents.EntityOxygenEvent.register(OxygenEvents::playerOxygenListener);
    }
}
