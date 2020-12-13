package com.thundersphun.foggingup.screen;

import com.thundersphun.foggingup.FoggingUp;
import com.thundersphun.foggingup.fogTypes.FogType;
import com.thundersphun.foggingup.util.ClientInstanceUtil;
import com.thundersphun.foggingup.util.IdType;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import static com.thundersphun.foggingup.screen.FogCustomizableScreen.BUTTONHEIGHT;

public class FogListEntry extends EntryListWidget.Entry<FogListEntry> {
	public static final int SPACING = 4;
	public static final int OFFSET = 10;
	public static final int TEXT_FIELD_WIDTH = 150;
	public static final int SLIDER_WIDTH = 50;

	private final FogSlider fogStopSlider;
	private final FogSlider fogDensitySlider;
	private final FogSlider fogStartSlider;
	private final ButtonWidget buttonUp;
	private final ButtonWidget buttonDown;
	private final ButtonWidget remove;
	private final TextFieldWidget idWidget; // TODO: make use of a suggester, as with commands
	private final ToggleButtonWidget enabled;
	private boolean toBeRemoved;
	private TexturedButtonWidget idTypeWidget;
	private IdType idType;

	public FogListEntry(FogListWidget parent) {
		this.toBeRemoved = false;

		int x = OFFSET;

		this.idWidget = new TextFieldWidget(ClientInstanceUtil.getClient().textRenderer, x, 0, TEXT_FIELD_WIDTH, BUTTONHEIGHT, LiteralText.EMPTY);
		this.idType = IdType.BIOME;
		this.idTypeWidget = this.idType.getWidget(x += TEXT_FIELD_WIDTH + SPACING, 0, this::onPress);
		this.enabled = new WorkingToggleButtonWidget(x += BUTTONHEIGHT + SPACING, 0, BUTTONHEIGHT, BUTTONHEIGHT, false);
		this.enabled.setTextureUV(0, 0, 20, 20, FoggingUp.BUTTONTEXTURES);

		x += BUTTONHEIGHT - SLIDER_WIDTH;

		this.fogStartSlider = new FogSlider(x += SLIDER_WIDTH + SPACING, 0, SLIDER_WIDTH, BUTTONHEIGHT, LiteralText.EMPTY, 0.1, 2);
		this.fogStopSlider = new FogSlider(x += SLIDER_WIDTH + SPACING, 0, SLIDER_WIDTH, BUTTONHEIGHT, LiteralText.EMPTY, 0.1, 2);
		this.fogDensitySlider = new FogSlider(x += SLIDER_WIDTH + SPACING, 0, SLIDER_WIDTH, BUTTONHEIGHT, LiteralText.EMPTY, 0.1, 2);

		this.buttonUp = FoggingUp.makeTexturedButton(x += SLIDER_WIDTH + SPACING, 0, 11, 7, 40, 40, 7, e -> parent.moveUp(this));
		this.buttonDown = FoggingUp.makeTexturedButton(x, 0, 11, 7, 40, 54, 7, e -> parent.moveDown(this));

		this.remove = FoggingUp.makeTexturedButton(x + 16, OFFSET, 0, 40, e -> this.toBeRemoved = true);
	}

	public FogListEntry(FogType fogType, FogListWidget parent) {
		this(parent);

		this.idWidget.setText(fogType.getId().toString());
		if (FoggingUp.getFogList().isDimension(fogType.getId())) {
			this.idType = IdType.DIMENSION;
			this.idTypeWidget = this.idType.getWidget(this.idTypeWidget.x, this.idTypeWidget.y, this::onPress);
		} else if(FoggingUp.getFogList().isBiome(fogType.getId())) {
			this.idType = IdType.BIOME;
			this.idTypeWidget = this.idType.getWidget(this.idTypeWidget.x, this.idTypeWidget.y, this::onPress);
		}

		this.enabled.setToggled(!fogType.fogEnabled());
		if (fogType.fogEnabled()) {
			this.fogStartSlider.setValue(fogType.getStartModifier());
			this.fogStopSlider.setValue(fogType.getEndModifier());
			this.fogDensitySlider.setValue(fogType.getDensityModifier());
		} else {
			this.fogStartSlider.setValue(-1);
			this.fogStopSlider.setValue(-1);
			this.fogDensitySlider.setValue(-1);
		}
	}

	public IdType getIdType() {
		return this.idType;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.idWidget.y = y;
		this.idTypeWidget.y = y;
		this.enabled.y = y;
		this.fogStartSlider.y = y;
		this.fogStopSlider.y = y;
		this.fogDensitySlider.y = y;
		this.buttonUp.y = y + SPACING / 2;
		this.buttonDown.y = y + BUTTONHEIGHT - 7 - SPACING / 2;
		this.remove.y = y;

		this.idWidget.render(matrices, mouseX, mouseY, tickDelta);
		this.idTypeWidget.render(matrices, mouseX, mouseY, tickDelta);
		this.enabled.render(matrices, mouseX, mouseY, tickDelta);
		this.fogStartSlider.render(matrices, mouseX, mouseY, tickDelta);
		this.fogStopSlider.render(matrices, mouseX, mouseY, tickDelta);
		this.fogDensitySlider.render(matrices, mouseX, mouseY, tickDelta);
		this.buttonUp.render(matrices, mouseX, mouseY, tickDelta);
		this.buttonDown.render(matrices, mouseX, mouseY, tickDelta);
		this.remove.render(matrices, mouseX, mouseY, tickDelta);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.idWidget.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return this.idWidget.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int keyCode) {
		return this.idWidget.charTyped(chr, keyCode);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return this.fogStartSlider.mouseDragged(mouseX, mouseY, button, deltaX, deltaY) ||
				this.fogStopSlider.mouseDragged(mouseX, mouseY, button, deltaX, deltaY) ||
				this.fogDensitySlider.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		boolean enabled = !this.enabled.isToggled();
		if (!enabled && this.fogStartSlider.mouseClicked(mouseX, mouseY, button)) {
			this.fogStopSlider.setValue(1);
			this.fogDensitySlider.setValue(1);
			this.enabled.setToggled(false);
			return true;
		}

		if (!enabled && this.fogStopSlider.mouseClicked(mouseX, mouseY, button)) {
			this.fogStartSlider.setValue(1);
			this.fogDensitySlider.setValue(1);
			this.enabled.setToggled(false);
			return true;
		}

		if (!enabled && this.fogDensitySlider.mouseClicked(mouseX, mouseY, button)) {
			this.fogStartSlider.setValue(1);
			this.fogStopSlider.setValue(1);
			this.enabled.setToggled(false);
			return true;
		}

		if (this.enabled.mouseClicked(mouseX, mouseY, button)) {
			if (this.enabled.isToggled()) {
				this.fogStartSlider.setValue(-1);
				this.fogStopSlider.setValue(-1);
				this.fogDensitySlider.setValue(-1);
			} else {
				this.fogStartSlider.setValue(1);
				this.fogStopSlider.setValue(1);
				this.fogDensitySlider.setValue(1);
			}
			return true;
		}

		return this.idWidget.mouseClicked(mouseX, mouseY, button) ||
				this.idTypeWidget.mouseClicked(mouseX, mouseY, button) ||
				this.fogStartSlider.mouseClicked(mouseX, mouseY, button) ||
				this.fogStopSlider.mouseClicked(mouseX, mouseY, button) ||
				this.fogDensitySlider.mouseClicked(mouseX, mouseY, button) ||
				this.buttonDown.mouseClicked(mouseX, mouseY, button) ||
				this.buttonUp.mouseClicked(mouseX, mouseY, button) ||
				this.remove.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.idWidget.mouseReleased(mouseX, mouseY, button) ||
				this.idTypeWidget.mouseReleased(mouseX, mouseY, button) ||
				this.fogStartSlider.mouseReleased(mouseX, mouseY, button) ||
				this.fogStopSlider.mouseReleased(mouseX, mouseY, button) ||
				this.fogDensitySlider.mouseReleased(mouseX, mouseY, button) ||
				this.buttonDown.mouseReleased(mouseX, mouseY, button) ||
				this.buttonUp.mouseReleased(mouseX, mouseY, button) ||
				this.remove.mouseReleased(mouseX, mouseY, button);
	}

	private void onPress(ButtonWidget e) {
		this.idType = this.idType.next();
		this.idTypeWidget = this.idType.getWidget(this.idTypeWidget.x, this.idTypeWidget.y, this::onPress);
	}

	public void tick() {
		this.idWidget.tick();
	}

	public boolean isActive() {
		return !this.toBeRemoved;
	}

	public boolean isEnabled() {
		return !this.enabled.isToggled();
	}

	public float[] getModifiers() {
		float[] values = new float[3];
		values[0] = (float) this.fogStartSlider.getValue();
		values[1] = (float) this.fogStopSlider.getValue();
		values[2] = (float) this.fogDensitySlider.getValue();
		return values;
	}

	public Identifier getId() {
		return new Identifier(this.idWidget.getText());
	}
}
