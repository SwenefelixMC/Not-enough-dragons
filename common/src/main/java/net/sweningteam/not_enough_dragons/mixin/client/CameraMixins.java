package net.sweningteam.not_enough_dragons.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixins {

    @Shadow
    private Entity entity;

    @Inject(method = "getMaxZoom",at = @At("HEAD"), cancellable = true)
    private void moveCammeraBackOnDragon(float maxZoom, CallbackInfoReturnable<Float> cir){
        if(this.entity != null && this.entity.isPassenger()){
            Entity vehicle = this.entity.getVehicle();
            if(vehicle instanceof Dragon){
                float customDistance = 10.0F;
                cir.setReturnValue(customDistance);
            }
        }
    }
}
