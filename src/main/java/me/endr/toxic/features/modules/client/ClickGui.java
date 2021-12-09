package me.endr.toxic.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.endr.toxic.Toxic;
import me.endr.toxic.event.events.ClientEvent;
import me.endr.toxic.features.command.Command;
import me.endr.toxic.features.gui.ToxicGui;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickGui
        extends Module {
    private static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = this.register(new Setting<>("Prefix", "."));
    public Setting<Boolean> snowing = this.register(new Setting<>("Snowing", true));
    public Setting<Boolean> customFov = this.register(new Setting<>("CustomFov", false));
    public Setting<Float> fov = this.register(new Setting<>("Fov", 150.0f, -180.0f, 180.0f));
    public Setting<Integer> red = this.register(new Setting<>("Red", 0, 0, 255));
    public Setting<Integer> green = this.register(new Setting<>("Green", 160, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<>("Blue", 0, 0, 255));
    public Setting<Integer> hoverAlpha = this.register(new Setting<>("Alpha", 180, 0, 255));
    public Setting<Integer> topRed = this.register(new Setting<>("SecondRed", 0, 0, 255));
    public Setting<Integer> topGreen = this.register(new Setting<>("SecondGreen", 83, 0, 255));
    public Setting<Integer> topBlue = this.register(new Setting<>("SecondBlue", 0, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 240, 0, 255));
    public Setting<Boolean> rainbow = this.register(new Setting<>("Rainbow", false));
    public Setting<rainbowMode> rainbowModeHud = this.register(new Setting<Object>("HRainbowMode", rainbowMode.Static, v -> this.rainbow.getValue()));
    public Setting<rainbowModeArray> rainbowModeA = this.register(new Setting<Object>("ARainbowMode", rainbowModeArray.Static, v -> this.rainbow.getValue()));
    public Setting<Integer> rainbowHue = this.register(new Setting<Object>("Delay", 240, 0, 600, v -> this.rainbow.getValue()));
    public Setting<Float> rainbowBrightness = this.register(new Setting<Object>("Brightness ", 150.0f, 1.0f, 255.0f, v -> this.rainbow.getValue()));
    public Setting<Float> rainbowSaturation = this.register(new Setting<Object>("Saturation", 150.0f, 1.0f, 255.0f, v -> this.rainbow.getValue()));
    private ToxicGui click;

    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        this.setInstance();
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (this.customFov.getValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.getValue());
        }
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                Toxic.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + Toxic.commandManager.getPrefix());
            }
            Toxic.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(ToxicGui.getClickGui());
    }

    @Override
    public void onLoad() {
        Toxic.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        Toxic.commandManager.setPrefix(this.prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof ToxicGui)) {
            this.disable();
        }
    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }
}

