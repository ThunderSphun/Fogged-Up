package com.thundersphun.foggingup.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

public class ClientInstanceUtil {
	public static PlayerEntity getPlayer() {
		return (PlayerEntity) getClient().cameraEntity;
	}

	public static World getWorld() {
		PlayerEntity player = getPlayer();
		return player == null ? null : player.world;
	}

	public static Camera getCamera() {
		return getClient().gameRenderer.getCamera();
	}

	public static MinecraftClient getClient() {
		return MinecraftClient.getInstance();
	}

	public static DimensionType getDimension() {
		World world = getWorld();
		return world == null ? null : world.getDimension();
	}

	public static Identifier getDimensionId() {
		World world = getWorld();
		return world == null ? null : world.getRegistryKey().getValue();
	}

	public static Biome getBiome() {
		World world = getWorld();
		PlayerEntity player = getPlayer();
		if (world == null || player == null) {
			return null;
		}
		return world.getBiome(player.getBlockPos());
	}

	public static Identifier getBiomeId() {
		World world = getWorld();
		PlayerEntity player = getPlayer();
		Biome biome = getBiome();
		if (world == null || player == null || biome == null) {
			return null;
		}

		return world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);
	}
}