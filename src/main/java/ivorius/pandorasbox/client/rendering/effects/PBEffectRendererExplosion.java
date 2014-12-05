package ivorius.pandorasbox.client.rendering.effects;

import ivorius.pandorasbox.effects.PBEffectExplode;
import ivorius.pandorasbox.effects.PBEffectNormal;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
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

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.2f, 0.0f);
            GL11.glScalef(scale, scale, scale);
            renderLights(entity.ticksExisted + partialTicks, lightColor, timePassed, 10);
            GL11.glPopMatrix();
        }
    }

    public static void renderLights(float ticks, int color, float alpha, int number)
    {
        float width = 2.5f;

        Tessellator tessellator = Tessellator.instance;

        float usedTicks = ticks / 200.0F;

        Random random = new Random(432L);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();

        for (int var7 = 0; (float) var7 < number; ++var7)
        {
            float xLogFunc = (((float) var7 / number * 28493.0f + ticks) / 10.0f) % 20.0f;
            if (xLogFunc > 10.0f)
            {
                xLogFunc = 20.0f - xLogFunc;
            }

            float yLogFunc = 1.0f / (1.0f + (float) Math.pow(2.71828f, -0.8f * xLogFunc) * ((1.0f / 0.01f) - 1.0f));

            float lightAlpha = yLogFunc;

            if (lightAlpha > 0.01f)
            {
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F + usedTicks * 90.0F, 0.0F, 0.0F, 1.0F);
                tessellator.startDrawing(6);
                float var8 = random.nextFloat() * 20.0F + 5.0F;
                float var9 = random.nextFloat() * 2.0F + 1.0F;
                tessellator.setColorRGBA_I(color, (int) (255.0F * alpha * lightAlpha));
                tessellator.addVertex(0.0D, 0.0D, 0.0D);
                tessellator.setColorRGBA_I(color, 0);
                tessellator.addVertex(-width * (double) var9, var8, (-0.5F * var9));
                tessellator.addVertex(width * (double) var9, var8, (-0.5F * var9));
                tessellator.addVertex(0.0D, var8, (1.0F * var9));
                tessellator.addVertex(-width * (double) var9, var8, (-0.5F * var9));
                tessellator.draw();
            }
        }

        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
}
