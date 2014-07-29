/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesBomberman extends PBEffectEntityBased
{
    public int bombs;

    public PBEffectEntitiesBomberman()
    {
    }

    public PBEffectEntitiesBomberman(int maxTicksAlive, double range, int bombs)
    {
        super(maxTicksAlive, range);
        this.bombs = bombs;
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        if (!world.isRemote)
        {
            int prevBombs = MathHelper.floor_double(prevRatio * bombs);
            int newBombs = MathHelper.floor_double(newRatio * bombs);
            int bombs = newBombs - prevBombs;

            for (int i = 0; i < bombs; i++)
            {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, entity.posX, entity.posY, entity.posZ, null);
                entitytntprimed.fuse = 45 + random.nextInt(20);

                world.spawnEntityInWorld(entitytntprimed);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("bombs", bombs);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        bombs = compound.getInteger("bombs");
    }
}
