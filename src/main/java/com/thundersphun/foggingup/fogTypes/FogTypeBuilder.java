package com.thundersphun.foggingup.fogTypes;

import com.thundersphun.foggingup.util.IdType;
import net.minecraft.util.Identifier;

public class FogTypeBuilder {
	private final IdType type;
	private float start;
	private float end;
	private float density;
	private Identifier id;
	private boolean enabled;

	public FogTypeBuilder(IdType type) {
		this.type = type;
		this.start = 1;
		this.end = 1;
		this.density = 1;
		this.id = new Identifier("");
		this.enabled = true;
	}

	public FogTypeBuilder() {
		this(null);
	}

	public FogTypeBuilder setStart(float start) {
		this.start = start;
		return this;
	}

	public FogTypeBuilder setEnd(float end) {
		this.end = end;
		return this;
	}

	public FogTypeBuilder setDensity(float density) {
		this.density = density;
		return this;
	}

	public FogTypeBuilder setId(Identifier id) {
		this.id = id;
		return this;
	}

	public FogTypeBuilder setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public FogType build() {
		if (this.type != null) {
			switch (this.type) {
				case BIOME:
					return new BiomeFogType(this.start, this.end, this.density, this.id, this.enabled);
				case DIMENSION:
					return new DimensionFogType(this.start, this.end, this.density, this.id, this.enabled);
			}
		}
		return new FogType(this.start, this.end, this.density, this.id, this.enabled);
	}
}
