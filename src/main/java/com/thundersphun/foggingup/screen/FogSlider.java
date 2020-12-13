package com.thundersphun.foggingup.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

public class FogSlider extends SliderWidget {
	private final double min;
	private final double max;
	private double fogValue;

	public FogSlider(int x, int y, int width, int height, Text text, double min, double max) {
		super(x, y, width, height, text, normalize(1, min, max));

		this.min = min;
		this.max = max;

		this.applyValue();
		this.updateMessage();
	}

	private static double normalize(double value, double min, double max) {
		return (value - min) / (max - min);
	}

	@Override
	protected void renderBg(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
		if (value >= 0) {
			super.renderBg(matrices, client, mouseX, mouseY);
		}
	}

	@Override
	public void updateMessage() {
		if (value >= 0) {
			setMessage(new LiteralText(String.format("%.1f", this.fogValue)));
		} else {
			setMessage(new TranslatableText("foggingup.slider.disabled"));
		}
	}

	@Override
	protected void applyValue() {
		this.fogValue = MathHelper.lerp(value, 0.1, 2.0);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (clicked(mouseX, mouseY)) {
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
		return false;
	}

	public double getValue() {
		return this.fogValue;
	}

	public void setValue(double newValue) {
		value = normalize(newValue, this.min, this.max);
		this.fogValue = newValue;

		this.applyValue();
		this.updateMessage();
	}
}
