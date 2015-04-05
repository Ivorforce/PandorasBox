package ivorius.pandorasbox.client.rendering.effects;

import ivorius.pandorasbox.effects.PBEffectExplode;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.boss.EntityDragon;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/**
 * Created by lukas on 05.12.14.
 */
public class PBEffectRendererExplosion implements PBEffectRenderer<PBEffectExplode>
{
    @Override
    public void renderBox(EntityPandorasBox entity, PBEffectExplode effect, float partialTicks)
    {
        if (!entity.isInvisible())
        {
            int lightColor = effect.burning ? 0xff0088 : 0xdd3377;

            float timePassed = (float) entity.getEffectTicksExisted() / (float) effect.maxTicksAlive;
            timePassed *= timePassed;
            timePassed *= timePassed;

            float scale = (timePassed * 0.3f) * effect.explosionRadius * 0.3f;

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.2f, 0.0f);
            GlStateManager.scale(scale, scale, scale);
            renderLights(entity.ticksExisted + partialTicks, lightColor, timePassed, 10);
            GlStateManager.popMatrix();
        }
    }

    public void renderLights(float ticks, int color, float alpha, int number)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        RenderHelper.disableStandardItemLighting();
        float f7 = ticks / 200.0F;
        float f8 = 0.0F;

        if (f7 > 0.8F)
        {
            f8 = (f7 - 0.8F) / 0.2F;
        }

        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -1.0F, -2.0F);

        for (int i = 0; (float)i < number; ++i)
        {
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(random.nextFloat() * 360.0F + f7 * 90.0F, 0.0F, 0.0F, 1.0F);
            worldrenderer.startDrawing(6);
            float f9 = random.nextFloat() * 20.0F + 5.0F + f8 * 10.0F;
            float f10 = random.nextFloat() * 2.0F + 1.0F + f8 * 2.0F;
            worldrenderer.setColorRGBA_I(color, (int)(255.0F * alpha * (1.0F - f8)));
            worldrenderer.addVertex(0.0D, 0.0D, 0.0D);
            worldrenderer.setColorRGBA_I(color, 0);
            worldrenderer.addVertex(-0.866D * (double)f10, (double)f9, (double)(-0.5F * f10));
            worldrenderer.addVertex(0.866D * (double)f10, (double)f9, (double)(-0.5F * f10));
            worldrenderer.addVertex(0.0D, (double)f9, (double)(1.0F * f10));
            worldrenderer.addVertex(-0.866D * (double)f10, (double)f9, (double)(-0.5F * f10));
            tessellator.draw();
        }

        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
    }
}
