package me.endr.toxic.features.modules.render;

import me.endr.toxic.event.events.Render3DEvent;
import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.modules.client.ClickGui;
import me.endr.toxic.features.setting.Setting;
import me.endr.toxic.util.ColorUtil;
import me.endr.toxic.util.RenderUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

public class BlockHighlight
        extends Module {
    private final Setting<Float> lineWidth = this.register(new Setting<>("LineWidth", 1.0f, 0.1f, 5.0f));
    private final Setting<Integer> cAlpha = this.register(new Setting<>("Alpha", 255, 0, 255));

    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block u look at.", Module.Category.RENDER, false, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ray.getBlockPos();
            RenderUtil.drawBlockOutline(blockpos, ClickGui.getInstance().rainbow.getValue() ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(), ClickGui.getInstance().blue.getValue(), this.cAlpha.getValue()), this.lineWidth.getValue(), false);
        }
    }
}

