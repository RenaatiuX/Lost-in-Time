package com.rena.lost.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.lost.client.model.EnderSnarlerModel;
import com.rena.lost.common.entities.EnderSnarler;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class EnderSnarlerRenderer extends GeoEntityRenderer<EnderSnarler> {
    public EnderSnarlerRenderer(EntityRendererManager renderManager) {
        super(renderManager, new EnderSnarlerModel());
    }

    @Override
    public RenderType getRenderType(EnderSnarler animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getEntityTexture(EnderSnarler entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }

    @Override
    protected void applyRotations(EnderSnarler entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float trans = 0.5F;
        float progress = 1F - (entityLiving.prevAttachChangeProgress + (entityLiving.attachChangeProgress - entityLiving.prevAttachChangeProgress) * partialTicks);
        if (entityLiving.getAttachmentFacing() == Direction.DOWN) {
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
            matrixStackIn.translate(0.0D, trans, 0.0D);
            if (entityLiving.prevPosY < entityLiving.getPosY()) {
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90 * (1 - progress)));
            } else {
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-90 * (1 - progress)));
            }
            matrixStackIn.translate(0.0D, -trans, 0.0D);
        } else if (entityLiving.getAttachmentFacing() == Direction.UP) {
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));
            matrixStackIn.translate(0.0D, -trans, 0.0D);

        } else {
            matrixStackIn.translate(0.0D, trans, 0.0D);
            switch (entityLiving.getAttachmentFacing()) {
                case NORTH:
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F * progress));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(0));
                    break;
                case SOUTH:
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F * progress));
                    break;
                case WEST:
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F - 90.0F * progress));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-90.0F));
                    break;
                case EAST:
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F * progress - 90F));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
                    break;
            }
            if (entityLiving.getMotion().y <= -0.001F) {
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-180.0F));
            }
            matrixStackIn.translate(0.0D, -trans, 0.0D);
        }
    }
}
