/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.mods.Psychedelicraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesDrug extends PBEffectEntityBased
{
    public final List<Pair<String, Float>> drugs = new ArrayList<>();

    public PBEffectEntitiesDrug()
    {
    }

    public PBEffectEntitiesDrug(int maxTicksAlive, double range, Collection<Pair<String, Float>> drugs)
    {
        super(maxTicksAlive, range);
        this.drugs.addAll(drugs);
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        for (Pair<String, Float> effect : drugs)
        {
            float prevStrength = (float) (prevRatio * strength * effect.getRight());
            float newStrength = (float) (newRatio * strength * effect.getRight());
            float drugStrength = newStrength - prevStrength;

            if (drugStrength > 0)
                Psychedelicraft.addDrugValue(entity, effect.getLeft(), drugStrength);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList drugList = new NBTTagList();
        for (Pair<String, Float> drug : drugs)
        {
            NBTTagCompound drugCmp = new NBTTagCompound();
            drugCmp.setString("drugName", drug.getLeft());
            drugCmp.setFloat("strength", drug.getRight());
            drugList.appendTag(drugCmp);
        }
        compound.setTag("drugs", drugList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        drugs.clear();
        NBTTagList drugList = compound.getTagList("drugs", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < drugList.tagCount(); i++)
        {
            NBTTagCompound drugCmp = drugList.getCompoundTagAt(i);
            drugs.add(new ImmutablePair<>(drugCmp.getString("drugName"), drugCmp.getFloat("strength")));
        }
    }
}
