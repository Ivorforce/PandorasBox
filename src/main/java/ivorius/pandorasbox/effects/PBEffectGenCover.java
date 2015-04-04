/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.PBNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
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
    public boolean hasFlag(World world, EntityPandorasBox entity, Random random, BlockPos pos)
    {
        if (overSurface)
        {
            if (!isReplacable(world, pos))
            {
                return false;
            }

            return world.isBlockNormalCube(pos.west(), false) || world.isBlockNormalCube(pos.east(), false)
                    || world.isBlockNormalCube(pos.down(), false) || world.isBlockNormalCube(pos.up(), false)
                    || world.isBlockNormalCube(pos.north(), false) || world.isBlockNormalCube(pos.south(), false);
        }
        else
        {
//            if (!world.isBlockNormalCubeDefault(pos, false))
//                return false;
//
            return isReplacable(world, pos.west()) || isReplacable(world, pos.east())
                    || isReplacable(world, pos.down()) || isReplacable(world, pos.up())
                    || isReplacable(world, pos.north()) || isReplacable(world, pos.south());
        }
    }

    private boolean isReplacable(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, BlockPos pos, double range, boolean flag)
    {
        if (flag)
        {
            Block newBlock = blocks[random.nextInt(blocks.length)];
            world.setBlockState(pos, newBlock.getDefaultState());
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("overSurface", overSurface);
        PBNBTHelper.writeNBTBlocks("blocks", blocks, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        overSurface = compound.getBoolean("overSurface");
        blocks = PBNBTHelper.readNBTBlocks("blocks", compound);
    }
}
