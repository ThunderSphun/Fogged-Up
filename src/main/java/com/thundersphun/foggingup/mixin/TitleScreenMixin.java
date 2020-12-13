package com.thundersphun.foggingup.mixin;

import com.thundersphun.foggingup.FoggingUp;
import com.thundersphun.foggingup.screen.FogCustomizableScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	private TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init()V", at = @At(value = "TAIL"))
	public void addFogCustomizableScreenButton(CallbackInfo ci) {
		int realmsX = width / 2 - 100;
		int realmsY = height / 4 + 96;
		int realmsW = 200;

		addButton(FoggingUp.openScreenButton(realmsX + realmsW + 4, realmsY, e -> client.openScreen(new FogCustomizableScreen(this))));
	}
}
