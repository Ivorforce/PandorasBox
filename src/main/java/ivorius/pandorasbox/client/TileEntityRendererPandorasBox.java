/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.client;

import ivorius.pandorasbox.PandorasBox;
import ivorius.pandorasbox.block.TileEntityPandorasBox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by lukas on 15.04.14.
 */
public class TileEntityRendererPandorasBox extends TileEntitySpecialRenderer
{
    public ModelBase model;
    public ResourceLocation texture;

    public TileEntityRendererPandorasBox()
    {
        model = new ModelPandorasBox();
        texture = new ResourceLocation(PandorasBox.MODID, PandorasBox.filePathTextures + "pbTexture.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float partialTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
        GL11.glRotatef(-((TileEntityPandorasBox) var1).boxRotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);

        EntityArrow emptyEntity = new EntityArrow(var1.getWorldObj());
        emptyEntity.rotationPitch = 0.0f / 180.0f * 3.1415926f;
        bindTexture(texture);
        model.render(emptyEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}
