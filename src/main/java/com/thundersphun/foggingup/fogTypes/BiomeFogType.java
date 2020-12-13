package com.thundersphun.foggingup.fogTypes;

import net.minecraft.util.Identifier;

public class BiomeFogType extends FogType {
	public static final FogType DEFAULT = new FogType(1, 1, 1, null, true);

	public BiomeFogType(float startModifier, float endModifier, float densityModifier, Identifier id, boolean enabled) {
		super(startModifier, endModifier, densityModifier, id, enabled);
	}

	public BiomeFogType(float startModifier, float endModifier, float densityModifier, Identifier id) {
		super(startModifier, endModifier, densityModifier, id);
	}

	public BiomeFogType(Identifier id) {
		super(id);
	}
}
