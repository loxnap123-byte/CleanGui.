package com.cleandev.cleangui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class CleanGuiScreen extends Screen {
    private static final String[] ITEMS = {"Settings", "Modules", "Cosmetics", "Profile", "FPS Boost", "About"};
    private static final int PURPLE = 0xFF9B55FF;
    private long animationStart = System.currentTimeMillis();
    private boolean closing = false;
    private boolean fpsBoostEnabled = false;
    private String selected = "Settings";

    public CleanGuiScreen() {
        super(Text.literal("Clean GUI"));
    }

    public void beginClose() {
        if (!closing) {
            closing = true;
            animationStart = System.currentTimeMillis();
        }
    }

    public void tickAnimation(MinecraftClient client) {
        if (closing && System.currentTimeMillis() - animationStart >= 220) {
            client.setScreen(null);
        }
    }

    private float opacity() {
        float t = MathHelper.clamp((System.currentTimeMillis() - animationStart) / 220.0f, 0.0f, 1.0f);
        return closing ? 1.0f - t : t;
    }

    private static int alpha(int color, float opacity) {
        int a = (color >>> 24) & 255;
        int newA = Math.round(a * opacity);
        return (color & 0x00FFFFFF) | (newA << 24);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        float a = opacity();
        int w = this.width;
        int h = this.height;
        ctx.fill(0, 0, w, h, alpha(0x99060910, a));

        int panelW = Math.min(920, w - 36);
        int panelH = 500;
        int x = (w - panelW) / 2;
        int y = (h - panelH) / 2;
        int slide = Math.round((1.0f - a) * 12);
        y += closing ? -slide : slide;

        // Layered rectangles create a subtle rounded-card impression without external textures.
        ctx.fill(x + 8, y, x + panelW - 8, y + panelH, alpha(0xF20D0E17, a));
        ctx.fill(x, y + 8, x + panelW, y + panelH - 8, alpha(0xF20D0E17, a));
        ctx.fill(x + 2, y + 2, x + panelW - 2, y + panelH - 2, alpha(0xFF11121D, a));
        ctx.fill(x + 2, y + 2, x + panelW - 2, y + 3, alpha(0xFF65409A, a));

        centered(ctx, "◇", w / 2, y + 22, alpha(PURPLE, a));
        centered(ctx, "Clean GUI", w / 2, y + 54, alpha(0xFFFFFFFF, a));
        centered(ctx, "Minecraft  •  Fabric 1.21.11", w / 2, y + 82, alpha(0xFFAAA9B8, a));
        ctx.fill(x + 190, y + 108, x + panelW - 190, y + 109, alpha(0xFF513276, a));

        int gap = 18;
        int buttonW = (panelW - 100 - gap) / 2;
        int buttonH = 64;
        int startX = x + 50;
        int startY = y + 132;
        for (int i = 0; i < ITEMS.length; i++) {
            int col = i % 2;
            int row = i / 2;
            int bx = startX + col * (buttonW + gap);
            int by = startY + row * (buttonH + 16);
            boolean hover = mouseX >= bx && mouseX <= bx + buttonW && mouseY >= by && mouseY <= by + buttonH;
            boolean active = selected.equals(ITEMS[i]);
            int bg = (hover || active) ? 0xFF292238 : 0xFF1B1C27;
            ctx.fill(bx + 5, by, bx + buttonW - 5, by + buttonH, alpha(bg, a));
            ctx.fill(bx, by + 5, bx + buttonW, by + buttonH - 5, alpha(bg, a));
            if (active || hover) {
                ctx.fill(bx + 5, by, bx + buttonW - 5, by + 2, alpha(PURPLE, a));
            }
            String label = ITEMS[i];
            if (label.equals("FPS Boost")) label += fpsBoostEnabled ? "  •  ON" : "  •  OFF";
            ctx.drawText(this.textRenderer, Text.literal(label), bx + 28, by + 25, alpha(0xFFF2F0FA, a), false);
            ctx.drawText(this.textRenderer, Text.literal("›"), bx + buttonW - 28, by + 22, alpha(0xFFB5AFC5, a), false);
        }

        int exitY = startY + 3 * (buttonH + 16) + 5;
        int exitX = x + panelW / 2 - 150;
        ctx.fill(exitX + 5, exitY, exitX + 295, exitY + 54, alpha(0xFF241A35, a));
        ctx.fill(exitX, exitY + 5, exitX + 300, exitY + 49, alpha(0xFF241A35, a));
        ctx.fill(exitX + 2, exitY + 2, exitX + 298, exitY + 3, alpha(PURPLE, a));
        centered(ctx, "Exit", w / 2, exitY + 20, alpha(PURPLE, a));
        ctx.drawText(this.textRenderer, Text.literal("CleanDev"), x + 24, y + panelH - 28, alpha(0xFFAAA9B8, a), false);
        String close = "×";
        ctx.drawText(this.textRenderer, Text.literal(close), x + panelW - 34, y + 18, alpha(0xFFFFFFFF, a), false);
        super.render(ctx, mouseX, mouseY, delta);
    }

    private void centered(DrawContext ctx, String text, int centerX, int y, int color) {
        ctx.drawText(this.textRenderer, Text.literal(text), centerX - this.textRenderer.getWidth(text) / 2, y, color, false);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        double mouseX = click.x();
        double mouseY = click.y();
        if (click.button() != 0 || closing) return false;
        int panelW = Math.min(920, this.width - 36);
        int panelH = 500;
        int x = (this.width - panelW) / 2;
        int y = (this.height - panelH) / 2;
        int startX = x + 50;
        int startY = y + 132;
        int gap = 18;
        int buttonW = (panelW - 100 - gap) / 2;
        int buttonH = 64;

        if (mouseX >= x + panelW - 52 && mouseX <= x + panelW - 12 && mouseY >= y + 12 && mouseY <= y + 52) {
            beginClose();
            return true;
        }
        for (int i = 0; i < ITEMS.length; i++) {
            int bx = startX + (i % 2) * (buttonW + gap);
            int by = startY + (i / 2) * (buttonH + 16);
            if (mouseX >= bx && mouseX <= bx + buttonW && mouseY >= by && mouseY <= by + buttonH) {
                selected = ITEMS[i];
                if (selected.equals("FPS Boost")) fpsBoostEnabled = !fpsBoostEnabled;
                return true;
            }
        }
        int exitY = startY + 3 * (buttonH + 16) + 5;
        int exitX = x + panelW / 2 - 150;
        if (mouseX >= exitX && mouseX <= exitX + 300 && mouseY >= exitY && mouseY <= exitY + 54) {
            beginClose();
            return true;
        }
        return true;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
