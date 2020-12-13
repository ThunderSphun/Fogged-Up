package com.thundersphun.foggingup.mixin;

import com.thundersphun.foggingup.FoggingUp;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "stop()V", at = @At("HEAD"))
	private void saveData(CallbackInfo ci) {
		FoggingUp.saveFogList();
	}
}