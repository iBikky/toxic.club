package me.endr.toxic.features.modules.render;

import me.endr.toxic.features.modules.*;
import me.endr.toxic.features.setting.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;

public class NoInterpolation extends Module
{
    Setting<Boolean> sneak;
    Setting<Boolean> anim;

    public NoInterpolation() {
        super("NoInterpolation", "sneak", Module.Category.RENDER, true, false, false);
        this.sneak = (Setting<Boolean>)this.register(new Setting("Sneak", true));
        this.anim = (Setting<Boolean>)this.register(new Setting("Animations", true));
    }

    @SubscribeEvent
    public void onRender(final TickEvent.RenderTickEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (this.anim.getValue()) {
            NoInterpolation.mc.world.playerEntities.stream().filter(t -> t != NoInterpolation.mc.player).forEach(entity -> {
                entity.limbSwing = 0.0f;
                entity.limbSwingAmount = 0.0f;
                entity.prevLimbSwingAmount = 0.0f;
                return;
            });
        }
        if (this.sneak.getValue()) {
            NoInterpolation.mc.world.playerEntities.stream().filter(t -> t != NoInterpolation.mc.player).forEach(entity -> entity.setSneaking(true));
        }
    }
}