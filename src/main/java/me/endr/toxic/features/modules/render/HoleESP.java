package me.endr.toxic.features.modules.render;

import java.awt.Color;
import me.endr.toxic.event.events.Render3DEvent;
import me.endr.toxic.util.BlockUtil;
import me.endr.toxic.util.render.ColorUtil;
import me.endr.toxic.util.render.RenderUtil;
import me.endr.toxic.features.setting.Setting;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.modules.client.ClickGui;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class HoleESP
        extends Module {
    public Setting<Boolean> renderOwn = this.register(new Setting<Boolean>("RenderOwn", true));
    public Setting<Boolean> fov = this.register(new Setting<Boolean>("InFov", false));
    public Setting<Boolean> rainbow = this.register(new Setting<Boolean>("Rainbow", true));
    private final Setting<Integer> range = this.register(new Setting<Integer>("RangeX", 4, 0, 10));
    private final Setting<Integer> rangeY = this.register(new Setting<Integer>("RangeY", 4, 0, 10));
    public Setting<Boolean> box = this.register(new Setting<Boolean>("Box", false));
    public Setting<Boolean> gradientBox = this.register(new Setting<Object>("Gradient", Boolean.valueOf(true), v -> this.box.getValue()));
    public Setting<Boolean> invertGradientBox = this.register(new Setting<Object>("ReverseGradient", Boolean.valueOf(false), v -> this.gradientBox.getValue()));
    public Setting<Boolean> outline = this.register(new Setting<Boolean>("Outline", false));
    public Setting<Boolean> gradientOutline = this.register(new Setting<Object>("GradientOutline", Boolean.valueOf(false), v -> this.outline.getValue()));
    public Setting<Boolean> invertGradientOutline = this.register(new Setting<Object>("ReverseOutline", Boolean.valueOf(false), v -> this.gradientOutline.getValue()));
    public Setting<Double> height = this.register(new Setting<Double>("Height", 0.0, -2.0, 2.0));
    private Setting<Integer> red = this.register(new Setting<Integer>("Red", 0, 0, 255));
    private Setting<Integer> green = this.register(new Setting<Integer>("Green", 255, 0, 255));
    private Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 0, 0, 255));
    private Setting<Integer> alpha = this.register(new Setting<Integer>("Alpha", 255, 0, 255));
    private Setting<Integer> boxAlpha = this.register(new Setting<Object>("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255), v -> this.box.getValue()));
    private Setting<Float> lineWidth = this.register(new Setting<Object>("LineWidth", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f), v -> this.outline.getValue()));
    public Setting<Boolean> safeColor = this.register(new Setting<Boolean>("BedrockColor", false));
    private Setting<Integer> safeRed = this.register(new Setting<Object>("BedrockRed", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.safeColor.getValue()));
    private Setting<Integer> safeGreen = this.register(new Setting<Object>("BedrockGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.safeColor.getValue()));
    private Setting<Integer> safeBlue = this.register(new Setting<Object>("BedrockBlue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.safeColor.getValue()));
    private Setting<Integer> safeAlpha = this.register(new Setting<Object>("BedrockAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.safeColor.getValue()));
    public Setting<Boolean> customOutline = this.register(new Setting<Object>("CustomLine", Boolean.valueOf(false), v -> this.outline.getValue()));
    private Setting<Integer> cRed = this.register(new Setting<Object>("OL-Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false));
    private Setting<Integer> cGreen = this.register(new Setting<Object>("OL-Green", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false));
    private Setting<Integer> cBlue = this.register(new Setting<Object>("OL-Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false));
    private Setting<Integer> cAlpha = this.register(new Setting<Object>("OL-Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false));
    private Setting<Integer> safecRed = this.register(new Setting<Object>("OL-SafeRed", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false && this.safeColor.getValue() != false));
    private Setting<Integer> safecGreen = this.register(new Setting<Object>("OL-SafeGreen", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false && this.safeColor.getValue() != false));
    private Setting<Integer> safecBlue = this.register(new Setting<Object>("OL-SafeBlue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false && this.safeColor.getValue() != false));
    private Setting<Integer> safecAlpha = this.register(new Setting<Object>("OL-SafeAlpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> this.customOutline.getValue() != false && this.outline.getValue() != false && this.safeColor.getValue() != false));
    private static HoleESP INSTANCE = new HoleESP();
    private int currentAlpha = 0;

    public HoleESP() {
        super("HoleESP", "Shows safe spots.", Module.Category.RENDER, false, false, false);
        this.setInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static HoleESP getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HoleESP();
        }
        return INSTANCE;
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        assert (HoleESP.mc.renderViewEntity != null);
        Vec3i playerPos = new Vec3i(HoleESP.mc.renderViewEntity.posX, HoleESP.mc.renderViewEntity.posY, HoleESP.mc.renderViewEntity.posZ);
        for (int x = playerPos.getX() - this.range.getValue(); x < playerPos.getX() + this.range.getValue(); ++x) {
            for (int z = playerPos.getZ() - this.range.getValue(); z < playerPos.getZ() + this.range.getValue(); ++z) {
                for (int y = playerPos.getY() + this.rangeY.getValue(); y > playerPos.getY() - this.rangeY.getValue(); --y) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || pos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ)) && !this.renderOwn.getValue().booleanValue() || !BlockUtil.isPosInFov(pos).booleanValue() && this.fov.getValue().booleanValue()) continue;
                    if (HoleESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
                        RenderUtil.drawBoxESP(pos, this.rainbow.getValue() != false ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.customOutline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), this.lineWidth.getValue().floatValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true, this.height.getValue(), this.gradientBox.getValue(), this.gradientOutline.getValue(), this.invertGradientBox.getValue(), this.invertGradientOutline.getValue(), this.currentAlpha);
                        continue;
                    }
                    if (!BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.down()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.east()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.west()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.south()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.north()).getBlock())) continue;
                    RenderUtil.drawBoxESP(pos, this.rainbow.getValue() != false ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), this.customOutline.getValue(), new Color(this.cRed.getValue(), this.cGreen.getValue(), this.cBlue.getValue(), this.cAlpha.getValue()), this.lineWidth.getValue().floatValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true, this.height.getValue(), this.gradientBox.getValue(), this.gradientOutline.getValue(), this.invertGradientBox.getValue(), this.invertGradientOutline.getValue(), this.currentAlpha);
                }
            }
        }
    }
}