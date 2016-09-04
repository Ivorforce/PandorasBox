/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;

/**
 * Created by lukas on 31.03.14.
 */
public class PBEffectMulti extends PBEffect
{
    public PBEffect[] effects;
    public int[] delays;

    public PBEffectMulti()
    {
    }

    public PBEffectMulti(PBEffect[] effects, int[] delays)
    {
        this.effects = effects;
        this.delays = delays;
    }

    @Override
    public void doTick(EntityPandorasBox entity, Vec3d effectCenter, int ticksAlive)
    {
        for (int i = 0; i < effects.length; i++)
        {
            int effectTicks = ticksAlive - delays[i];
            effects[i].doTick(entity, effectCenter, effectTicks);
        }
    }

    @Override
    public boolean isDone(EntityPandorasBox entity, int ticksAlive)
    {
        for (int i = 0; i < effects.length; i++)
        {
            int effectTicks = ticksAlive - delays[i];
            if (!effects[i].isDone(entity, effectTicks))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < effects.length; i++)
        {
            NBTTagCompound cmp = new NBTTagCompound();

            cmp.setInteger("delay", delays[i]);

            cmp.setString("pbEffectID", effects[i].getEffectID());
            NBTTagCompound effectCmp = new NBTTagCompound();
            effects[i].writeToNBT(effectCmp);
            cmp.setTag("pbEffectCompound", effectCmp);

            list.appendTag(cmp);
        }

        compound.setTag("effects", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("effects", Constants.NBT.TAG_COMPOUND);

        effects = new PBEffect[list.tagCount()];
        delays = new int[effects.length];

        for (int i = 0; i < effects.length; i++)
        {
            NBTTagCompound cmp = list.getCompoundTagAt(i);

            delays[i] = cmp.getInteger("delay");
            effects[i] = PBEffectRegistry.loadEffect(cmp.getString("pbEffectID"), cmp.getCompoundTag("pbEffectCompound"));
        }
    }

    @Override
    public boolean canGenerateMoreEffectsAfterwards(EntityPandorasBox entity)
    {
        for (PBEffect effect : effects)
        {
            if (!effect.canGenerateMoreEffectsAfterwards(entity))
            {
                return false;
            }
        }

        return true;
    }
}
