package com.thundersphun.foggingup.util;

import com.thundersphun.foggingup.fogTypes.FogType;

public enum RenderType {
	START(Float.MIN_VALUE),
	END(Float.MAX_VALUE),
	DENSITY(0);

	private final float invisible;

	RenderType(float invisible) {
		this.invisible = invisible;
	}

	public float getModifier(FogType fogType) {
		switch (this) {
			case START:
				return fogType.getStartModifier();
			case END:
				return fogType.getEndModifier();
			case DENSITY:
				return fogType.getDensityModifier();
		}
		return 1;
	}

	public float getInvisible() {
		return this.invisible;
	}
}
