package com.thundersphun.foggingup.mixin;

import com.thundersphun.foggingup.FoggingUp;
import com.thundersphun.foggingup.screen.FogCustomizableScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
	private GameMenuScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "initWidgets()V", at = @At(value = "TAIL"))
	public void addFogCustomizableScreenButton(CallbackInfo ci) {
		int lanX = width / 2 + 4;
		int lanY = height / 4 + 80;
		int lanW = 98;

		addButton(FoggingUp.openScreenButton(lanX + lanW + 4, lanY, e -> client.openScreen(new FogCustomizableScreen(this))));
	}
}
