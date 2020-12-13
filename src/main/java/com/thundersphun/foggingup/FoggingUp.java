package com.thundersphun.foggingup;

import com.thundersphun.foggingup.data.FogIO;
import com.thundersphun.foggingup.data.FogList;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class FoggingUp implements ClientModInitializer {
	public static final Identifier BUTTONTEXTURES = new Identifier("foggingup", "gui/buttons.png");
	public static final int TEXTURE_WIDTH = 64;
	public static final int TEXTURE_HEIGHT = 128;
	private static FogIO IO;
	private static FogList fogList;

	public static FogList getFogList() {
		return fogList;
	}

	public static void setFogList(FogList newFogList) {
		fogList = newFogList;
	}

	public static TexturedButtonWidget makeTexturedButton(int x, int y, int w, int h, int u, int v, int vOffset, ButtonWidget.PressAction onPress) {
		return new TexturedButtonWidget(x, y, w, h, u, v, vOffset, BUTTONTEXTURES, TEXTURE_WIDTH, TEXTURE_HEIGHT, onPress);
	}

	public static TexturedButtonWidget makeTexturedButton(int x, int y, int u, int v, ButtonWidget.PressAction onPress) {
		return makeTexturedButton(x, y, 20, 20, u, v, 20, onPress);
	}

	public static TexturedButtonWidget openScreenButton(int x, int y, ButtonWidget.PressAction onPress) {
		return makeTexturedButton(x, y, 20, 20, 40, 0, 20, onPress);
	}

	public static void saveFogList() {
		IO.save(FoggingUp.getFogList());
	}

	@Override
	public void onInitializeClient() {
		IO = new FogIO();
		fogList = IO.toList();
	}
}
