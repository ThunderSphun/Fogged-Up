package com.thundersphun.foggingup.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thundersphun.foggingup.FoggingUp;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.util.math.MatrixStack;

public class WorkingToggleButtonWidget extends ToggleButtonWidget {
	public WorkingToggleButtonWidget(int x, int y, int width, int height, boolean toggled) {
		super(x, y, width, height, toggled);
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		toggled = !toggled;
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		minecraftClient.getTextureManager().bindTexture(texture);
		RenderSystem.disableDepthTest();
		int i = u;
		int j = v;
		if (toggled) {
			i += pressedUOffset;
		}

		if (isHovered()) {
			j += hoverVOffset;
		}

		drawTexture(matrices, x, y, i, j, width, height, FoggingUp.TEXTURE_WIDTH, FoggingUp.TEXTURE_HEIGHT);
		RenderSystem.enableDepthTest();
	}
}
