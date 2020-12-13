package com.thundersphun.foggingup.screen;

import com.thundersphun.foggingup.FoggingUp;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class FogListWidget extends EntryListWidget<FogListEntry> {
	private boolean moved;

	public FogListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
		super(client, width, height, top, bottom, itemHeight);
		this.moved = false;

		FoggingUp.getFogList().getFogTypes().forEach(e -> addEntry(new FogListEntry(e, this)));

		setRenderHeader(true, itemHeight);
	}

	@Override
	public int getRowLeft() {
		return left;
	}

	@Override
	protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator tessellator) {
		TextRenderer renderer = client.textRenderer;
		x = FogListEntry.OFFSET + FogListEntry.TEXT_FIELD_WIDTH / 2;
		y += FogListEntry.OFFSET / 2;
		DrawableHelper.drawCenteredText(matrices, renderer, new TranslatableText("foggedup.field.id"), x, y, 0xffffff);
		x += FogListEntry.TEXT_FIELD_WIDTH + FogCustomizableScreen.BUTTONHEIGHT * 2 + FogListEntry.SPACING * 2 - FogListEntry.SLIDER_WIDTH;
		DrawableHelper.drawCenteredText(matrices, renderer, new TranslatableText("foggedup.slider.start"), x, y, 0xffffff);
		x += FogListEntry.SPACING + FogListEntry.SLIDER_WIDTH;
		DrawableHelper.drawCenteredText(matrices, renderer, new TranslatableText("foggedup.slider.end"), x, y, 0xffffff);
		x += FogListEntry.SPACING + FogListEntry.SLIDER_WIDTH;
		DrawableHelper.drawCenteredText(matrices, renderer, new TranslatableText("foggedup.slider.density"), x, y, 0xffffff);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		updateScrollingState(mouseX, mouseY, button);
		boolean clicked = false;
		for (FogListEntry entry : children()) {
			if (entry.isActive() && entry.mouseClicked(mouseX, mouseY, button)) {
				setFocused(entry);
				setDragging(true);
				clicked = true;
			}
		}
		return clicked || super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		boolean value = false;
		for (FogListEntry entry : children()) {
			if (entry.isActive() && entry.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
				value = true;
			}
		}
		return value || super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		boolean value = false;
		for (FogListEntry entry : children()) {
			if (entry.isActive() && entry.keyPressed(keyCode, scanCode, modifiers)) {
				value = true;
			}
		}
		return value || super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		boolean value = false;
		for (FogListEntry entry : children()) {
			if (entry.isActive() && entry.keyReleased(keyCode, scanCode, modifiers)) {
				value = true;
			}
		}
		return value || super.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int keyCode) {
		return children().stream().filter(FogListEntry::isActive).anyMatch(e -> e.charTyped(chr, keyCode));
	}

	@Override
	public int getRowWidth() {
		return width;
	}

	@Override
	protected int getScrollbarPositionX() {
		return width - FogListEntry.OFFSET - FogListEntry.SPACING;
	}

	@Override
	public int addEntry(FogListEntry entry) {
		return super.addEntry(entry);
	}

	public void tick() {
		children().removeIf(e -> !e.isActive());
		children().stream().filter(FogListEntry::isActive).forEach(FogListEntry::tick);
		this.moved = false;
	}

	public void moveUp(FogListEntry fogListEntry) {
		int i = children().indexOf(fogListEntry);
		if (!this.moved && i != 0) {
			this.moved = true;
			children().remove(fogListEntry);
			children().add(i - 1, fogListEntry);
		}
	}

	public void moveDown(FogListEntry fogListEntry) {
		int i = children().indexOf(fogListEntry);
		if (!this.moved && i != children().size() - 1) {
			this.moved = true;
			children().remove(fogListEntry);
			children().add(i + 1, fogListEntry);
		}
	}
}
