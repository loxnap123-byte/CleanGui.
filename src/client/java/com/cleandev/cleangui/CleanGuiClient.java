package com.cleandev.cleangui;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class CleanGuiClient implements ClientModInitializer {
    private static KeyBinding openMenu;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = new KeyBinding.Category(Identifier.of("cleangui", "main"));
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cleangui.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.consumeClick()) {
                if (client.currentScreen instanceof CleanGuiScreen screen) {
                    screen.beginClose();
                } else if (client.currentScreen == null) {
                    client.setScreen(new CleanGuiScreen());
                }
            }
            if (client.currentScreen instanceof CleanGuiScreen screen) {
                screen.tickAnimation(client);
            }
        });
    }
}
