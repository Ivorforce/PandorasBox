package ivorius.pandorasbox.client.rendering.effects;

import ivorius.pandorasbox.effects.PBEffect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukas on 05.12.14.
 */
public class PBEffectRenderingRegistry
{
    private static final Map<Class, PBEffectRenderer> renderers = new HashMap<>();

    public static <E extends PBEffect> void registerRenderer(Class clazz, PBEffectRenderer<E> renderer)
    {
        renderers.put(clazz, renderer);
    }

    public static PBEffectRenderer rendererForEffect(PBEffect effect)
    {
        Class currClass = effect.getClass();

        do
        {
            PBEffectRenderer renderer = renderers.get(currClass);
            if (renderer != null)
                return renderer;
        }
        while ((currClass = currClass.getSuperclass()) != null);

        return null;
    }
}
