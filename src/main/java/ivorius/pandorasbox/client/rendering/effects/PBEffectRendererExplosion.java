package ivorius.pandorasbox.client.rendering.effects;

import ivorius.ivtoolkit.rendering.IvRenderHelper;
import ivorius.pandorasbox.effects.PBEffectExplode;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.client.renderer.*;

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
            IvRenderHelper.renderLights(entity.ticksExisted + partialTicks, lightColor, timePassed, 10);
            GlStateManager.popMatrix();
        }
    }
}
