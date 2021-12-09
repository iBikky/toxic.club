package me.endr.toxic.features.modules.render;

import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Wireframe
        extends Module {
    private static Wireframe INSTANCE = new Wireframe();
    public final Setting<Float> alpha = this.register(new Setting<>("PAlpha", 255.0f, 0.1f, 255.0f));
    public final Setting<Float> cAlpha = this.register(new Setting<>("CAlpha", 255.0f, 0.1f, 255.0f));
    public final Setting<Float> lineWidth = this.register(new Setting<>("PLineWidth", 1.0f, 0.1f, 3.0f));
    public final Setting<Float> crystalLineWidth = this.register(new Setting<>("CLineWidth", 1.0f, 0.1f, 3.0f));
    public Setting<RenderMode> mode = this.register(new Setting<>("PMode", RenderMode.SOLID));
    public Setting<RenderMode> cMode = this.register(new Setting<>("CMode", RenderMode.SOLID));
    public Setting<Boolean> players = this.register(new Setting<>("Players", Boolean.FALSE));
    public Setting<Boolean> playerModel = this.register(new Setting<>("PlayerModel", Boolean.FALSE));
    public Setting<Boolean> crystals = this.register(new Setting<>("Crystals", Boolean.FALSE));
    public Setting<Boolean> crystalModel = this.register(new Setting<>("CrystalModel", Boolean.FALSE));

    public Wireframe() {
        super("Wireframe", "Draws a wireframe esp around other players.", Module.Category.RENDER, false, false, false);
        this.setInstance();
    }

    public static Wireframe getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Wireframe();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onRenderPlayerEvent(RenderPlayerEvent.Pre event) {
        event.getEntityPlayer().hurtTime = 0;
    }

    public enum RenderMode {
        SOLID,
        WIREFRAME

    }
}

