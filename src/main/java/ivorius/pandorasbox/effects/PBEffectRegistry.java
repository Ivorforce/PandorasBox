/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectRegistry
{
    private static Hashtable<String, Class<? extends PBEffect>> registeredEffects = new Hashtable<String, Class<? extends PBEffect>>();

    public static Class<? extends PBEffect> getEffect(String id)
    {
        return registeredEffects.get(id);
    }

    public static void register(Class<? extends PBEffect> effect, String id)
    {
        registeredEffects.put(id, effect);
    }

    public static Set<String> getAllEffectIDs()
    {
        return registeredEffects.keySet();
    }

    public static Collection<Class<? extends PBEffect>> getAllEffects()
    {
        return registeredEffects.values();
    }

    public static void writeEffect(PBEffect effect, NBTTagCompound compound)
    {
        if (effect != null)
        {
            compound.setString("pbEffectID", effect.getEffectID());
            NBTTagCompound pbEffectCompound = new NBTTagCompound();
            effect.writeToNBT(pbEffectCompound);
            compound.setTag("pbEffectCompound", pbEffectCompound);
        }
    }

    public static PBEffect loadEffect(NBTTagCompound compound)
    {
        return loadEffect(compound.getString("pbEffectID"), compound.getCompoundTag("pbEffectCompound"));
    }

    public static PBEffect loadEffect(String id, NBTTagCompound compound)
    {
        Class<? extends PBEffect> clazz = getEffect(id);

        PBEffect effect = null;

        if (clazz != null)
        {
            try
            {
                effect = clazz.newInstance();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        if (effect != null && compound != null)
        {
            effect.readFromNBT(compound);
            return effect;
        }
        else
        {
            System.err.println("Pandoras Box: Could not load effect with id '" + id + "'!");
        }

        return null;
    }

    public static String getEffectID(PBEffect effect)
    {
        Class<? extends PBEffect> clazz = effect.getClass();

        for (String id : registeredEffects.keySet())
        {
            if (registeredEffects.get(id).equals(clazz))
            {
                return id;
            }
        }

        return null;
    }
}
