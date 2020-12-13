package com.thundersphun.foggingup.fogTypes;

import com.thundersphun.foggingup.util.RenderType;
import net.minecraft.util.Identifier;

public class FogType {
	public static final FogType IN_LAVA = new FogType(0f, 3f, 1f, null, true);
	public static final FogType IN_LAVA_FIRE_RES = new FogType(0.25f, 1f, 1f, null, true);

	protected float startModifier;
	protected float endModifier;
	protected float densityModifier;
	protected Identifier id;
	protected boolean enabled;

	public FogType(float startModifier, float endModifier, float densityModifier, Identifier id, boolean enabled) {
		this.startModifier = startModifier;
		this.endModifier = endModifier;
		this.densityModifier = densityModifier;
		this.id = id;
		this.enabled = enabled;
	}

	public FogType(float startModifier, float endModifier, float densityModifier, Identifier id) {
		this(startModifier, endModifier, densityModifier, id, true);
	}

	public FogType(Identifier id) {
		this(RenderType.START.getInvisible(), RenderType.END.getInvisible(), RenderType.DENSITY.getInvisible(), id, false);
	}

	public float getStartModifier() {
		return this.startModifier;
	}

	public float getEndModifier() {
		return this.endModifier;
	}

	public float getDensityModifier() {
		return this.densityModifier;
	}

	public Identifier getId() {
		return this.id;
	}

	public boolean fogEnabled() {
		return this.enabled;
	}
}
