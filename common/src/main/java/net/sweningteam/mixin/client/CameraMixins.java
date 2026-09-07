package net.sweningteam.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.sweningteam.common.entity.dragons.base.Dragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
