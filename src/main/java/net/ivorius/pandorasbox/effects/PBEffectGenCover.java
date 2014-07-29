/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 12.04.14.
 */
public class PBEffectGenCover extends PBEffectGenerateByFlag
{
    public boolean overSurface;
    public Block[] blocks;

    public PBEffectGenCover()
    {
    }

    public PBEffectGenCover(int time, double range, int unifiedSeed, boolean overSurface, Block[] blocks)
    {
        super(time, range, 1, unifiedSeed);
        this.overSurface = overSurface;
        this.blocks = blocks;
    }

    @Override
    public boolean hasFlag(World world, EntityPandorasBox entity, Random random, int x, int y, int z)
    {
        if (overSurface)
        {
            if (!isReplacable(world, x, y, z))
            {
                return false;
            }

            return world.isBlockNormalCubeDefault(x - 1, y, z, false) || world.isBlockNormalCubeDefault(x + 1, y, z, false)
                    || world.isBlockNormalCubeDefault(x, y - 1, z, false) || world.isBlockNormalCubeDefault(x, y + 1, z, false)
                    || world.isBlockNormalCubeDefault(x, y, z - 1, false) || world.isBlockNormalCubeDefault(x, y, z + 1, false);
        }
        else
        {
//            if (!world.isBlockNormalCubeDefault(x, y, z, false))
//                return false;
//
            return isReplacable(world, x - 1, y, z) || isReplacable(world, x + 1, y, z)
                    || isReplacable(world, x, y - 1, z) || isReplacable(world, x, y + 1, z)
                    || isReplacable(world, x, y, z - 1) || isReplacable(world, x, y, z + 1);
        }
    }

    private boolean isReplacable(World world, int x, int y, int z)
    {
        return world.getBlock(x, y, z).isReplaceable(world, x, y, z);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, boolean flag, double range)
    {
        if (flag)
        {
            Block newBlock = blocks[random.nextInt(blocks.length)];
            world.setBlock(x, y, z, newBlock);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("overSurface", overSurface);
        setNBTBlocks("blocks", blocks, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        overSurface = compound.getBoolean("overSurface");
        blocks = getNBTBlocks("blocks", compound);
    }
}
