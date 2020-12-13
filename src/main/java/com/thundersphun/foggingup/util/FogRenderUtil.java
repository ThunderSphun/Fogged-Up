package com.thundersphun.foggingup.util;

import com.thundersphun.foggingup.FoggingUp;
import com.thundersphun.foggingup.data.FogList;
import com.thundersphun.foggingup.fogTypes.FogType;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public class FogRenderUtil {
	@Nullable
	public static FogType getFogType() {
		Identifier dimension = ClientInstanceUtil.getDimensionId();
		Identifier biome = ClientInstanceUtil.getBiomeId();
		PlayerEntity player = ClientInstanceUtil.getPlayer();
		Camera camera = ClientInstanceUtil.getCamera();
		FogList fogTypes = FoggingUp.getFogList();

		if (player != null && camera.getSubmergedFluidState().isIn(FluidTags.LAVA)) {
			return player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) ? FogType.IN_LAVA_FIRE_RES : FogType.IN_LAVA;
		} else if (fogTypes.contains(dimension) && fogTypes.contains(biome)) {
			int dimIndex = fogTypes.getFogTypes().stream().map(FogType::getId).collect(Collectors.toList()).lastIndexOf(dimension);
			int biomeIndex = fogTypes.getFogTypes().stream().map(FogType::getId).collect(Collectors.toList()).lastIndexOf(biome);

			if (dimIndex > biomeIndex) {
				return fogTypes.getDimensionFogType(dimension);
			} else if (dimIndex < biomeIndex) {
				return fogTypes.getBiomeFogType(biome);
			}
		} else if (fogTypes.contains(dimension)) {
			return fogTypes.getDimensionFogType(dimension);
		} else if (fogTypes.contains(biome)) {
			return fogTypes.getBiomeFogType(biome);
		}
		return null;
	}

	public static float getRenderModifier(float value, FogType fogType, RenderType type) { // TODO: make it use biome blend
		if (fogType == null || !FogRenderUtil.shouldRender()) {
			return value;
		} else if (fogType == FogType.IN_LAVA || fogType == FogType.IN_LAVA_FIRE_RES) {
			return type.getModifier(fogType);
		} else if (fogType.fogEnabled()) {
			return value * type.getModifier(fogType);
		} else {
			return type.getInvisible();
		}
	}

	public static boolean shouldRender() {
		PlayerEntity player = ClientInstanceUtil.getPlayer();
		return player != null && !player.hasStatusEffect(StatusEffects.BLINDNESS);
	}
}
