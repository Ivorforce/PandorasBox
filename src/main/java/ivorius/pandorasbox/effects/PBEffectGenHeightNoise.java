/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnSurface(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, BlockPos pos, double range, int pass)
    {
        if (!world.isRemote)
        {
            int randomX = pos.getX() - (pos.getX() % blockSize);
            int randomZ = pos.getZ() - (pos.getZ() % blockSize);
            Random usedRandom = new Random(new Random(randomX).nextLong() ^ new Random(randomZ).nextLong());

            int shift = minShift + usedRandom.nextInt(maxShift - minShift + 1);
            int towerSize = minTowerSize + usedRandom.nextInt(maxTowerSize - minTowerSize + 1);

            int towerMinY = pos.getY() - towerSize / 2;
            int minEffectY = towerMinY + Math.min(0, shift);
            int maxEffectY = towerMinY + towerSize + Math.max(0, shift);

            List<EntityPlayer> entityList = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.fromBounds(pos.getX() - 2.0, minEffectY - 4, pos.getZ() - 3.0, pos.getX() + 4.0, maxEffectY + 4, pos.getZ() + 4.0));

            if (entityList.size() == 0)
            {
                IBlockState[] states = new IBlockState[towerSize];

                for (int y = 0; y < towerSize; y++)
                    states[y] = world.getBlockState(pos.up(towerMinY));

                for (int y = 0; y < towerSize; y++)
                    setBlockSafe(world, pos.up(towerMinY), states[y]);
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
