/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.client.rendering;

import com.google.common.base.Function;
import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.client.rendering.effects.PBEffectRenderer;
import ivorius.pandorasbox.client.rendering.effects.PBEffectRenderingRegistry;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Created by lukas on 30.03.14.
 */
@SideOnly(Side.CLIENT)
public class RenderPandorasBox extends Render
{
    public ModelBase model;
    public ResourceLocation texture;

//    public ResourceLocation model;

    public RenderPandorasBox(RenderManager renderManager)
    {
        super(renderManager);

        model = new ModelPandorasBox();
        texture = new ResourceLocation(PandorasBox.MODID, "textures/pbTexture.png");

//        model = new ResourceLocation(PandorasBox.MODID, "block/pandoras_box.b3d");
    }

    public static void renderB3DModel(TextureManager textureManager, ResourceLocation modelLoc, int animationCounter)
    {
        IModel model = null;
        try
        {
            model = ModelLoaderRegistry.getModel(modelLoc);
            B3DLoader.B3DState defaultState = (B3DLoader.B3DState) model.getDefaultState();
            B3DLoader.B3DState newState = new B3DLoader.B3DState(defaultState.getAnimation(), animationCounter);
            renderBlockModel(textureManager, model, newState);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void renderBlockModel(TextureManager textureManager, IModel model, IModelState state)
    {
        if (state == null)
            state = model.getDefaultState();

        // Temporary fix for some models having alpha=0
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();

        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();

        VertexFormat vFormat = Attributes.DEFAULT_BAKED_FORMAT;
        IFlexibleBakedModel bakedModel = model.bake(state, vFormat, input -> input == null ? textureMapBlocks.getMissingSprite() : textureMapBlocks.getAtlasSprite(input.toString()));
        textureManager.bindTexture(TextureMap.locationBlocksTexture);

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5f, 0.0f, -0.5f);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, vFormat);
//        worldRenderer.markDirty(); // Still required?

        for (BakedQuad quad : bakedModel.getGeneralQuads())
            worldRenderer.addVertexData(quad.getVertexData());
        for (EnumFacing facing : EnumFacing.values())
            for (BakedQuad quad : bakedModel.getFaceQuads(facing))
                worldRenderer.addVertexData(quad.getVertexData());

        tessellator.draw();

        GlStateManager.popMatrix();

        GlStateManager.enableAlpha(); // End temporary fix
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
    {
        EntityPandorasBox entityPandorasBox = (EntityPandorasBox) entity;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + MathHelper.sin((entity.ticksExisted + partialTicks) * 0.04f) * 0.05, z);
        GlStateManager.rotate(-yaw, 0.0F, 1.0F, 0.0F);

        PBEffect effect = entityPandorasBox.getBoxEffect();
        if (!effect.isDone(entityPandorasBox, entityPandorasBox.getEffectTicksExisted()) && entityPandorasBox.getDeathTicks() < 0)
        {
            PBEffectRenderer renderer = PBEffectRenderingRegistry.rendererForEffect(effect);
            if (renderer != null)
                renderer.renderBox(entityPandorasBox, effect, partialTicks);
        }

        if (!entity.isInvisible())
        {
            float boxScale = entityPandorasBox.getCurrentScale();
            if (boxScale < 1.0f)
                GlStateManager.scale(boxScale, boxScale, boxScale);

            GlStateManager.translate(0.0f, 1.5f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            EntityArrow emptyEntity = new EntityArrow(entity.worldObj);
            emptyEntity.rotationPitch = entityPandorasBox.getRatioBoxOpen(partialTicks) * 120.0f / 180.0f * 3.1415926f;
            bindEntityTexture(entity);
            model.render(emptyEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

//            int animationCounter = Math.min(89, MathHelper.floor_float((entityPandorasBox.getRatioBoxOpen(partialTicks) + 0.5f) * 90));
//            renderB3DModel(renderManager.renderEngine, model, animationCounter);
        }

        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, yaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1)
    {
        return texture;
    }
}
