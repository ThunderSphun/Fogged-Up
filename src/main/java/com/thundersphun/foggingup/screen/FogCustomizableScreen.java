package com.thundersphun.foggingup.screen;

import com.thundersphun.foggingup.FoggingUp;
import com.thundersphun.foggingup.data.FogList;
import com.thundersphun.foggingup.fogTypes.FogTypeBuilder;
import com.thundersphun.foggingup.util.IdType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class FogCustomizableScreen extends Screen {
	public static final int BUTTONHEIGHT = 20;
	private final Screen parent;
	private FogListWidget widget;

	public FogCustomizableScreen(Screen parent) {
		super(new TranslatableText("foggingup.screen.customizable.title"));

		this.parent = parent;
	}

	@Override
	protected void init() {
		client.keyboard.setRepeatEvents(true);
		int buttonWidth = 150;
		int horizontalSpacing = 4;
		int verticalSpacing = 8;

		this.widget = addChild(new FogListWidget(client, width, height, BUTTONHEIGHT * 2,
				height - BUTTONHEIGHT * 2, BUTTONHEIGHT + horizontalSpacing / 2));

		addButton(new ButtonWidget(width / 2 + horizontalSpacing / 2, height - BUTTONHEIGHT - verticalSpacing,
				buttonWidth, BUTTONHEIGHT, ScreenTexts.BACK, e -> this.onClose()));
		addButton(new ButtonWidget(width / 2 - buttonWidth - horizontalSpacing / 2, height - BUTTONHEIGHT - verticalSpacing,
				buttonWidth, BUTTONHEIGHT, ScreenTexts.DONE, e -> {
			this.save();
			this.onClose();
		}));
		addButton(FoggingUp.makeTexturedButton(width / 2 + horizontalSpacing + buttonWidth, height - BUTTONHEIGHT - verticalSpacing,
				20, 40, e -> this.widget.addEntry(new FogListEntry(this.widget))));
	}

	@Override
	public void tick() {
		super.tick();
		this.widget.tick();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackgroundTexture(0);
		this.widget.render(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, textRenderer, title, width / 2, 15, 0xffffff);
	}

	public void removed() {
		client.keyboard.setRepeatEvents(false);
	}

	@Override
	public boolean charTyped(char chr, int keyCode) {
		return this.widget.charTyped(chr, keyCode);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return this.widget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	private void save() {
		FogList fogList = new FogList();
		for (FogListEntry e : widget.children()) {
			FogTypeBuilder builder = new FogTypeBuilder();
			if (e.getIdType() == IdType.BIOME) {
				builder = new FogTypeBuilder(IdType.BIOME);
			}
			if (e.getIdType() == IdType.DIMENSION) {
				builder = new FogTypeBuilder(IdType.DIMENSION);
			}

			builder.setEnabled(e.isEnabled());
			float[] modifiers = e.getModifiers();
			builder.setStart(modifiers[0]);
			builder.setEnd(modifiers[1]);
			builder.setDensity(modifiers[2]);

			builder.setId(e.getId());
			fogList.add(builder.build());
		}
		FoggingUp.setFogList(fogList);

		FoggingUp.saveFogList();
	}

	@Override
	public void onClose() {
		client.openScreen(this.parent);
	}
}
