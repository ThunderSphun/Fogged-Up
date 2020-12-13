package com.thundersphun.foggingup.data;

import com.thundersphun.foggingup.fogTypes.BiomeFogType;
import com.thundersphun.foggingup.fogTypes.DimensionFogType;
import com.thundersphun.foggingup.fogTypes.FogType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FogList {
	public static final FogList DEFAULT_TYPES = new FogList(
			new DimensionFogType(new Identifier("minecraft:the_nether")),
			new BiomeFogType(1.1857142f, 1.2309524f, 1.2083334f, new Identifier("minecraft:plains")),
			new BiomeFogType(0.83511907f, 0.8464286f, 0.63154763f, new Identifier("minecraft:mushroom_fields")),
			new BiomeFogType(0.83511907f, 0.8464286f, 0.63154763f, new Identifier("minecraft:mushroom_fields_shore")),
			new BiomeFogType(0.3375f, 0.3488095f, 0.5410714f, new Identifier("minecraft:ice_spikes")));

	private final List<FogType> fogTypes;

	public FogList() {
		this.fogTypes = new ArrayList<>();
	}

	public FogList(FogType... types) {
		this();
		this.fogTypes.addAll(Arrays.asList(types));
	}

	public List<FogType> getFogTypes() {
		return this.fogTypes;
	}

	public List<FogType> getDimensionFogTypes() {
		return this.fogTypes.stream().filter(e -> e instanceof DimensionFogType).collect(Collectors.toList());
	}

	public List<FogType> getBiomeFogTypes() {
		return this.fogTypes.stream().filter(e -> e instanceof BiomeFogType).collect(Collectors.toList());
	}

	public boolean contains(Identifier id) {
		return this.fogTypes.stream().anyMatch(e -> e.getId().equals(id));
	}

	public boolean isBiome(Identifier id) {
		return getBiomeFogTypes().stream().anyMatch(e -> e.getId().equals(id));
	}

	public boolean isDimension(Identifier id) {
		return getDimensionFogTypes().stream().anyMatch(e -> e.getId().equals(id));
	}

	public FogType getFogType(Identifier id) {
		if (!this.contains(id)) {
			return null;
		}
		return this.fogTypes.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
	}

	public DimensionFogType getDimensionFogType(Identifier id) {
		if (!this.contains(id)) {
			return null;
		}
		FogType fogType = getFogType(id);
		if (!this.isDimension(fogType.getId())) {
			return null;
		}
		return (DimensionFogType) fogType;
	}

	public BiomeFogType getBiomeFogType(Identifier id) {
		if (!this.contains(id)) {
			return null;
		}
		FogType fogType = getFogType(id);
		if (!this.isBiome(fogType.getId())) {
			return null;
		}
		return (BiomeFogType) fogType;
	}

	public int add(FogType fogType) {
		this.fogTypes.add(fogType);
		return this.fogTypes.size();
	}
}
