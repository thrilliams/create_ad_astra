package co.aethre.create_ad_astra.mixin.client;

import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import earth.terrarium.adastra.api.systems.OxygenApi;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RemainingAirOverlay.class)
abstract class RemainingAirOverlayMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isInLava()Z"
            )
    )
    private static boolean isInLavaOrWithoutOxygen(LocalPlayer player) {
        return player.isInLava() || !OxygenApi.API.hasOxygen(player);
    }
}
