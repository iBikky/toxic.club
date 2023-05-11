package me.endr.toxic.features.modules.client;

// imports

import me.endr.toxic.Toxic;
import me.endr.toxic.event.events.Render2DEvent;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import me.endr.toxic.util.ColorUtil;
import me.endr.toxic.util.RenderUtil;
import me.endr.toxic.util.Timer;

public class CSGOWatermark extends Module {

    Timer delayTimer = new Timer();
    public Setting<Integer> X = this.register(new Setting("WatermarkX", 10, 0, 300));
    public Setting<Integer> Y = this.register(new Setting("WatermarkY", 10, 0, 300));
    public Setting<Integer> Z = this.register(new Setting("WatermarkZ", 10, 0, 300));
    public Setting<Integer> delay = this.register(new Setting<Object>("Delay", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(600)));
    public Setting<Integer> saturation = this.register(new Setting<Object>("Saturation", 127, 1, 255));
    public Setting<Integer> brightness = this.register(new Setting<Object>("Brightness", 100, 0, 255));

    public float hue;
    public int red = 1;
    public int green = 1;
    public int blue = 1;

    private String message = "";
    public CSGOWatermark() {
        super("Watermark", "noatmc", Module.Category.CLIENT, true, false, false);
    }

    @Override
    public void onRender2D ( Render2DEvent event ) {
        drawCsgoWatermark();
    }

    public void drawCsgoWatermark () {
        int padding = 5;
        message = "toxic.club v0.2 | " + mc.player.getName() + " | " + Toxic.serverManager.getPing() + "ms";
        Integer textWidth = mc.fontRenderer.getStringWidth(message); // taken from wurst+ 3
        Integer textHeight = mc.fontRenderer.FONT_HEIGHT; // taken from wurst+ 3

        RenderUtil.drawRectangleCorrectly(X.getValue() - 4, Y.getValue() - 4, textWidth + 16, textHeight + 12, ColorUtil.toRGBA(22, 22, 22, 255));
        RenderUtil.drawRectangleCorrectly(X.getValue(), Y.getValue(), textWidth + 4, textHeight + 4, ColorUtil.toRGBA(0, 0, 0, 255));
        RenderUtil.drawRectangleCorrectly(X.getValue(), Y.getValue(), textWidth + 8, textHeight + 4, ColorUtil.toRGBA(0, 0, 0, 255));
        RenderUtil.drawRectangleCorrectly(X.getValue(), Y.getValue(), textWidth + 8,  1, ColorUtil.rainbow(this.delay.getValue()).hashCode());
        Toxic.textManager.drawString(message, X.getValue() + 3, Y.getValue() + 3, ColorUtil.toRGBA(255, 255, 255, 255), false);
    }
}