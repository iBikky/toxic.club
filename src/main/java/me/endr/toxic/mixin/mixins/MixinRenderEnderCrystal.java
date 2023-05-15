package me.endr.toxic.mixin.mixins;

import net.minecraft.entity.item.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import me.endr.toxic.features.modules.render.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.*;
import javax.annotation.*;

@Mixin({ RenderEnderCrystal.class })
public class MixinRenderEnderCrystal extends Render<EntityEnderCrystal>
{
    @Shadow
    public ModelBase modelEnderCrystal;
    @Final
    @Shadow
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;

    protected MixinRenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
    }

    @Redirect(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void bottomRenderRedirect(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        GlStateManager.scale((float)ModifyCrystal.scale.getValue(), (float)ModifyCrystal.scale.getValue(), (float)ModifyCrystal.scale.getValue());
        modelBase.render(entityIn, limbSwing, limbSwingAmount * ModifyCrystal.getSpeed()[0], ageInTicks * ModifyCrystal.getSpeed()[1], netHeadYaw, headPitch, scale);
    }

    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = { @At("RETURN") }, cancellable = true)
    public void doRenderCrystal(final EntityEnderCrystal entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        final float f3 = entity.innerRotation + partialTicks;
        float f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
        f4 += f4 * f4;
        if (entity.shouldShowBottom()) {
            this.modelEnderCrystal.render((Entity)entity, 0.0f, f3 * 3.0f * ModifyCrystal.getSpeed()[0], f4 * 0.2f * ModifyCrystal.getSpeed()[1], 0.0f, 0.0f, 0.0625f);
        }
    }

    @Nullable
    protected ResourceLocation getEntityTexture(final EntityEnderCrystal entity) {
        return null;
    }
}