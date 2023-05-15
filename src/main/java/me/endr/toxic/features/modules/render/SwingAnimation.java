package me.endr.toxic.features.modules.render;

import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SwingAnimation extends Module {
    public static Setting<AnimationVersion> AnimationsVersion;
    public static Setting<Boolean> playersDisableAnimations;
    public static Setting<Boolean> changeMainhand;
    public static Setting<Float> mainhand;
    public static Setting<Boolean> changeOffhand;
    public static Setting<Float> offhand;
    public static Setting<Integer> changeSwing;
    public static Setting<Integer> swingDelay;

    public SwingAnimation() {
        super("SwingAnimation", "Allows you to change animations in your hand", Module.Category.RENDER, true, false, false);
        AnimationsVersion = this.register(new Setting<AnimationVersion>("Version", AnimationVersion.OneDotEight));
        playersDisableAnimations = this.register(new Setting<Boolean>("Disable Animations", false));
        changeMainhand = this.register(new Setting<Boolean>("Change Mainhand", true));
        mainhand = this.register(new Setting<Float>("Mainhand", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(4.7509747f) ^ 0x7F1807FC)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.63819E38f) ^ 0x7EF67CC9)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(30.789412f) ^ 0x7E7650B7))));
        changeOffhand = this.register(new Setting<Boolean>("Change Offhand", true));
        offhand = this.register(new Setting<Float>("Offhand", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(15.8065405f) ^ 0x7EFCE797)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(3.3688825E38f) ^ 0x7F7D7251)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(7.3325067f) ^ 0x7F6AA3E5))));
        changeSwing = this.register(new Setting<Integer>("Swing Speed", 6, 0, 20));
        swingDelay = this.register(new Setting<Integer>("Swing Delay", 6, 1, 20));
    }

    @Override
    public void onUpdate() {
        if (playersDisableAnimations.getValue().booleanValue()) {
            for (EntityPlayer player : SwingAnimation.mc.world.playerEntities) {
                player.limbSwing = Float.intBitsToFloat(Float.floatToIntBits(1.8755627E38f) ^ 0x7F0D1A06);
                player.limbSwingAmount = Float.intBitsToFloat(Float.floatToIntBits(6.103741E37f) ^ 0x7E37AD83);
                player.prevLimbSwingAmount = Float.intBitsToFloat(Float.floatToIntBits(4.8253957E37f) ^ 0x7E11357F);
            }
        }
        if (changeMainhand.getValue().booleanValue() && SwingAnimation.mc.entityRenderer.itemRenderer.equippedProgressMainHand != mainhand.getValue().floatValue()) {
            SwingAnimation.mc.entityRenderer.itemRenderer.equippedProgressMainHand = mainhand.getValue().floatValue();
            SwingAnimation.mc.entityRenderer.itemRenderer.itemStackMainHand = SwingAnimation.mc.player.getHeldItemMainhand();
        }
        if (changeOffhand.getValue().booleanValue() && SwingAnimation.mc.entityRenderer.itemRenderer.equippedProgressOffHand != offhand.getValue().floatValue()) {
            SwingAnimation.mc.entityRenderer.itemRenderer.equippedProgressOffHand = offhand.getValue().floatValue();
            SwingAnimation.mc.entityRenderer.itemRenderer.itemStackOffHand = SwingAnimation.mc.player.getHeldItemOffhand();
        }
        if (AnimationsVersion.getValue() == AnimationVersion.OneDotEight && (double) SwingAnimation.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            SwingAnimation.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            SwingAnimation.mc.entityRenderer.itemRenderer.itemStackMainHand = SwingAnimation.mc.player.getHeldItemMainhand();
        }
    }

    public static enum AnimationVersion {
        OneDotEight,
        OneDotTwelve;

    }
}