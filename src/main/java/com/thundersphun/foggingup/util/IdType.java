package com.thundersphun.foggingup.util;

import com.thundersphun.foggingup.FoggingUp;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

import java.util.Arrays;

public enum IdType {
	BIOME(0, 80),
	DIMENSION(20, 80);

	private final int u;
	private final int v;

	IdType(int u, int v) {
		this.u = u;
		this.v = v;
	}

	public IdType next() {
		IdType[] values = values();
		int i = Arrays.asList(values).indexOf(this) + 1;
		return values[i % values.length];
	}

	public TexturedButtonWidget getWidget(int x, int y, ButtonWidget.PressAction onPress) {
		return FoggingUp.makeTexturedButton(x, y, this.u, this.v, onPress);
	}
}
