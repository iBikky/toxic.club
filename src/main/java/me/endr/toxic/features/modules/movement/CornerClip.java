package me.endr.toxic.features.modules.movement;


import me.endr.toxic.features.modules.Module;
import me.endr.toxic.features.setting.Setting;
import me.endr.toxic.util.MathUtil;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;

public class CornerClip
        extends Module {
    public final Setting<Mode> mode = this.register(new Setting<Mode>("CornerClip Mode", Mode.Vertical));
    public final Setting<VMode> vmode = this.register(new Setting<Object>("V Mode", (Object)VMode.Bypass, v -> this.mode.getValue() == Mode.Vertical));
    public final Setting<HMode> hmode = this.register(new Setting<Object>("H Mode", (Object)HMode.Normal, v -> this.mode.getValue() == Mode.Horizontal));
    public final Setting<Boolean> disables = this.register(new Setting<Boolean>("Disables", true));
    public final Setting<Integer> CornerClipAmount = this.register(new Setting<Integer>("CornerClip Amount", -3, -50, 50));
    public final Setting<Boolean> autoAdjust = this.register(new Setting<Boolean>("Auto Adjust", true));
    public final Setting<Double> offset = this.register(new Setting<Double>("Offset", -4.0, -70.0, 70.0));
    boolean isSneaking;

    public CornerClip() {
        super("CornerClip", "Automatically CornerClips you into blocks", Module.Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (CornerClip.nullCheck()) {
            return;
        }
        Vec3d dir = MathUtil.direction(CornerClip.mc.player.rotationYaw);
        if (this.mode.getValue() == Mode.Vertical && this.vmode.getValue() == VMode.Normal) {
            CornerClip.mc.player.setPosition(CornerClip.mc.player.posX, CornerClip.mc.player.posY + (double)this.CornerClipAmount.getValue().intValue(), CornerClip.mc.player.posZ);
            if (this.disables.getValue().booleanValue()) {
                this.disable();
            }
        }
        if (this.mode.getValue() == Mode.Horizontal && this.hmode.getValue() == HMode.Normal) {
            CornerClip.mc.player.setPosition(CornerClip.mc.player.posX + dir.x * (double)this.CornerClipAmount.getValue().intValue(), CornerClip.mc.player.posY, CornerClip.mc.player.posZ + dir.z * (double)this.CornerClipAmount.getValue().intValue());
            if (this.disables.getValue().booleanValue()) {
                this.disable();
            }
        }
        if (this.mode.getValue() == Mode.Vertical && this.vmode.getValue() == VMode.Bypass) {
            if (this.autoAdjust.getValue().booleanValue()) {
                this.offset.setValue(-CornerClip.mc.player.posY - 2.0);
            }
            if (this.isSneaking) {
                CornerClip.mc.player.connection.sendPacket(new CPacketEntityAction(CornerClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
            CornerClip.mc.player.connection.sendPacket(new CPacketPlayer.Position(CornerClip.mc.player.posX, CornerClip.mc.player.posY + this.offset.getValue(), CornerClip.mc.player.posZ, false));
            CornerClip.mc.player.connection.sendPacket(new CPacketPlayer.Position(CornerClip.mc.player.posX, CornerClip.mc.player.posY + 0.41999998688698, CornerClip.mc.player.posZ, true));
            CornerClip.mc.player.connection.sendPacket(new CPacketPlayer.Position(CornerClip.mc.player.posX, CornerClip.mc.player.posY + 0.7531999805211997, CornerClip.mc.player.posZ, true));
            CornerClip.mc.player.connection.sendPacket(new CPacketPlayer.Position(CornerClip.mc.player.posX, CornerClip.mc.player.posY + 1.00133597911214, CornerClip.mc.player.posZ, true));
            CornerClip.mc.player.connection.sendPacket(new CPacketPlayer.Position(CornerClip.mc.player.posX, CornerClip.mc.player.posY + 1.16610926093821, CornerClip.mc.player.posZ, true));
            CornerClip.mc.player.connection.sendPacket(new CPacketEntityAction(CornerClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            CornerClip.mc.player.setSneaking(false);
        }
    }

    @Override
    public void onDisable() {
        if (CornerClip.mc.player == null) {
            return;
        }
        if (this.isSneaking) {
            CornerClip.mc.player.connection.sendPacket(new CPacketEntityAction(CornerClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
    }

    @Override
    public void onEnable() {
        if (CornerClip.mc.player == null) {
            return;
        }
        if (this.mode.getValue() == Mode.Vertical && this.vmode.getValue() == VMode.Bypass) {
            CornerClip.mc.player.jump();
        }
    }

    public static enum HMode {
        Normal;

    }

    public static enum VMode {
        Normal,
        Bypass;

    }

    public static enum Mode {
        Vertical,
        Horizontal;

    }}