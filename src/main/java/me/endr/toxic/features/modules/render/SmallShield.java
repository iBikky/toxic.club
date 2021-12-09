package me.endr.toxic.features.modules.render;

import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;

public class SmallShield
        extends Module {
    private static SmallShield INSTANCE = new SmallShield();
    public Setting<Float> offX = this.register(new Setting<>("OffHandX", 0.0f, -1.0f, 1.0f));
    public Setting<Float> offY = this.register(new Setting<>("OffHandY", 0.0f, -1.0f, 1.0f));
    public Setting<Float> mainX = this.register(new Setting<>("MainHandX", 0.0f, -1.0f, 1.0f));
    public Setting<Float> mainY = this.register(new Setting<>("MainHandY", 0.0f, -1.0f, 1.0f));

    public SmallShield() {
        super("SmallShield", "Makes you offhand lower.", Module.Category.RENDER, false, false, false);
        this.setInstance();
    }

    public static SmallShield getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new SmallShield();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

