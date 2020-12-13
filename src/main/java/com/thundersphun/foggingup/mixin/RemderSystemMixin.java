package com.thundersphun.foggingup.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thundersphun.foggingup.fogTypes.FogType;
import com.thundersphun.foggingup.util.FogRenderUtil;
import com.thundersphun.foggingup.util.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RenderSystem.class)
public abstract class RemderSystemMixin {
	@ModifyArg(method = "fogDensity(F)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;fogDensity(F)V"))
	private static float density(float density) {
		FogType fogType = FogRenderUtil.getFogType();
		return FogRenderUtil.getRenderModifier(density, fogType, RenderType.DENSITY);
	}

	@ModifyArg(method = "fogStart(F)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;fogStart(F)V"))
	private static float start(float start) {
		FogType fogType = FogRenderUtil.getFogType();
		return FogRenderUtil.getRenderModifier(start, fogType, RenderType.START);
	}

	@ModifyArg(method = "fogEnd(F)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;fogEnd(F)V"))
	private static float end(float end) {
		FogType fogType = FogRenderUtil.getFogType();
		return FogRenderUtil.getRenderModifier(end, fogType, RenderType.END);
	}
}


