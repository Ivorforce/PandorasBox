/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenHeightNoise extends PBEffectGenerate2D
{
    public int minShift;
    public int maxShift;

    public int minTowerSize;
    public int maxTowerSize;

    public int blockSize;

    public PBEffectGenHeightNoise()
    {
    }

    public PBEffectGenHeightNoise(int time, double range, int unifiedSeed, int minShift, int maxShift, int minTowerSize, int maxTowerSize, int blockSize)
    {
        super(time, range, 1, unifiedSeed);

        this.minShift = minShift;
        this.maxShift = maxShift;

        this.minTowerSize = minTowerSize;
        this.maxTowerSize = maxTowerSize;

        this.blockSize = blockSize;
    }

    @Override
    public void generateOnSurface(World world, EntityPandorasBox entity, Random random, int pass, int x, int baseY, int z, double range)
    {
        if (!world.isRemote)
        {
            int randomX = x - (x % blockSize);
            int randomZ = z - (z % blockSize);
            Random usedRandom = new Random(new Random(randomX).nextLong() ^ new Random(randomZ).nextLong());

            int shift = minShift + usedRandom.nextInt(maxShift - minShift + 1);
            int towerSize = minTowerSize + usedRandom.nextInt(maxTowerSize - minTowerSize + 1);

            int towerMinY = baseY - towerSize / 2;
            int minEffectY = towerMinY + Math.min(0, shift);
            int maxEffectY = towerMinY + towerSize + Math.max(0, shift);

            List<EntityPlayer> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getAABBPool().getAABB(x - 2.0, minEffectY - 4, z - 3.0, x + 4.0, maxEffectY + 4, z + 4.0));

            if (entityList.size() == 0)
            {
                Block[] ids = new Block[towerSize];
                int[] metas = new int[towerSize];

                for (int y = 0; y < towerSize; y++)
                {
                    ids[y] = world.getBlock(x, towerMinY + y, z);
                    metas[y] = world.getBlockMetadata(x, towerMinY + y, z);
                }

                for (int y = 0; y < towerSize; y++)
                {
                    setBlockAndMetaSafe(world, x, towerMinY + y + shift, z, ids[y], metas[y]);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("minShift", minShift);
        compound.setInteger("maxShift", maxShift);
        compound.setInteger("minTowerSize", minTowerSize);
        compound.setInteger("maxTowerSize", maxTowerSize);
        compound.setInteger("blockSize", blockSize);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        minShift = compound.getInteger("minShift");
        maxShift = compound.getInteger("maxShift");
        minTowerSize = compound.getInteger("minTowerSize");
        maxTowerSize = compound.getInteger("maxTowerSize");
        blockSize = compound.getInteger("blockSize");
    }
}
