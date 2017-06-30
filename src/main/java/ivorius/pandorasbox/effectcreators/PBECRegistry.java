/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ivorius.pandorasbox.PBConfig;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectMulti;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECRegistry
{
    public static final int MAX_DELAY_IN_MULTIEFFECT = 60;

    private static final ArrayList<String> goodCreators = new ArrayList<>();
    private static final ArrayList<String> badCreators = new ArrayList<>();

    private static final Map<String, Float> fixedChanceCreators = new HashMap<>();

    private static final BiMap<String, PBEffectCreator> effectCreators = HashBiMap.create();

    public static void register(PBEffectCreator creator, String id, boolean good)
    {
        (good ? goodCreators : badCreators).add(id);
        effectCreators.put(id, creator);
    }

    public static void register(PBEffectCreator creator, String id, float fixedChance)
    {
        fixedChanceCreators.put(id, fixedChance);
        effectCreators.put(id, creator);
    }

    public static String getID(PBEffectCreator creator)
    {
        return effectCreators.inverse().get(creator);
    }

    public static PBEffectCreator effectCreatorWithName(String name)
    {
        return effectCreators.get(name);
    }

    public static boolean hasEffect(String name)
    {
        return effectCreators.containsKey(name);
    }

    public static boolean isEffectGood(String name)
    {
        return goodCreators.contains(name);
    }

    public static boolean isEffectBad(String name)
    {
        return badCreators.contains(name);
    }

    public static PBEffectCreator randomEffectCreatorOfType(Random random, boolean good)
    {
        ArrayList<String> list = good ? goodCreators : badCreators;
        return effectCreators.get(list.get(random.nextInt(list.size())));
    }

    public static PBEffect createEffect(World world, Random random, double x, double y, double z, String name)
    {
        PBEffectCreator creator = effectCreatorWithName(name);

        if (creator != null)
            return constructEffectSafe(creator, world, x, y, z, random);

        return null;
    }

    public static PBEffect createRandomEffectOfType(World world, Random random, double x, double y, double z, boolean good)
    {
        PBEffectCreator creator = randomEffectCreatorOfType(random, good);

        if (creator != null)
            return constructEffectSafe(creator, world, x, y, z, random);

        return null;
    }

    public static PBEffect createRandomEffect(World world, Random random, double x, double y, double z, boolean multi)
    {
        float currentMinChance = 1.0f;
        ArrayList<PBEffect> effects = new ArrayList<>();

        do
        {
            PBEffectCreator creator = null;

            for (String fixedChanceCreator : fixedChanceCreators.keySet())
            {
                if (random.nextFloat() < fixedChanceCreators.get(fixedChanceCreator))
                {
                    creator = effectCreators.get(fixedChanceCreator);
                    break;
                }
            }

            if (creator == null)
                creator = randomEffectCreatorOfType(random, random.nextFloat() < PBConfig.goodEffectChance);

            PBEffect effect = constructEffectSafe(creator, world, x, y, z, random);

            if (effect != null)
                effects.add(effect);

            currentMinChance = Math.min(currentMinChance, creator.chanceForMoreEffects(world, x, y, z, random));
        }
        while (random.nextFloat() < newEffectChance(currentMinChance) && effects.size() < PBConfig.maxEffectsPerBox && multi);

        if (effects.size() == 1)
        {
            return effects.get(0);
        }
        else
        {
            PBEffect[] effectArray = effects.toArray(new PBEffect[effects.size()]);
            int[] delays = new int[effectArray.length];

            for (int i = 1; i < delays.length; i++)
                delays[i] = random.nextInt(MAX_DELAY_IN_MULTIEFFECT);

            return new PBEffectMulti(effectArray, delays);
        }
    }

    private static double newEffectChance(double effectFactor)
    {
        return Math.pow(effectFactor, 1.0 / PBConfig.boxIntensity);
    }

    public static PBEffect constructEffectSafe(PBEffectCreator creator, World world, double x, double y, double z, Random random)
    {
        try
        {
            return creator.constructEffect(world, x, y, z, random);
        }
        catch (Exception ex)
        {
            System.out.println("PBEC failed! '" + getID(creator) + "'");
            ex.printStackTrace();
        }

        return null;
    }

    public static EntityPandorasBox spawnPandorasBox(World world, Random random, boolean multi, Entity entity)
    {
        PBEffect effect = createRandomEffect(world, random, entity.posX, entity.posY + 1.2, entity.posZ, multi);
        return spawnPandorasBox(world, effect, entity);
    }

    public static EntityPandorasBox spawnPandorasBox(World world, Random random, String id, Entity entity)
    {
        PBEffect effect = createEffect(world, random, entity.posX, entity.posY + 1.2, entity.posZ, id);
        return spawnPandorasBox(world, effect, entity);
    }

    public static EntityPandorasBox spawnPandorasBox(World world, PBEffect effect, Entity entity)
    {
        if (effect != null)
        {
            EntityPandorasBox entityPandorasBox = new EntityPandorasBox(world, effect);

            Vec3d look = entity.getLookVec();

            double spawnX = entity.posX + look.x * 0.5;
            double spawnY = entity.posY + 0.9 + look.y * 0.5;
            double spawnZ = entity.posZ + look.z * 0.5;

            entityPandorasBox.setLocationAndAngles(spawnX, spawnY, spawnZ, entity.rotationYaw + 180.0f, 0.0f);

            entityPandorasBox.beginFloatingAway();

            world.spawnEntity(entityPandorasBox);

            return entityPandorasBox;
        }

        return null;
    }

    public static Set<String> getAllIDs()
    {
        return effectCreators.keySet();
    }

    public static String[] getIDArray()
    {
        Set<String> allIDs = getAllIDs();
        return allIDs.toArray(new String[allIDs.size()]);
    }
}
