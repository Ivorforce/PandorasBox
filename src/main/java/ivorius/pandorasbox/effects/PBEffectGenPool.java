/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenPool extends PBEffectGenerate
{
    public Block block;
    public Block platformBlock;

    public PBEffectGenPool()
    {
    }

    public PBEffectGenPool(int time, double range, int unifiedSeed, Block block, Block platformBlock)
    {
        super(time, range, 1, unifiedSeed);

        this.block = block;
        this.platformBlock = platformBlock;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        if (!world.isRemote)
        {
            if (world.isBlockNormalCubeDefault(x, y, z, false))
            {
                boolean setPlatform = false;

                if (platformBlock != null)
                {
                    List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - 2.5, y - 2.5, z - 2.5, x + 3.5, y + 3.5, z + 3.5));

                    if (entityList.size() > 0)
                    {
                        setPlatform = true;
                    }
                }

                if (setPlatform)
                {
                    setBlockVarying(world, x, y, z, platformBlock, unifiedSeed);
                }
                else
                {
                    setBlockVarying(world, x, y, z, block, unifiedSeed);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("block", Block.blockRegistry.getNameForObject(block));

        if (platformBlock != null)
        {
            compound.setString("platformBlock", Block.blockRegistry.getNameForObject(platformBlock));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        block = (Block) Block.blockRegistry.getObject(compound.getString("block"));
        platformBlock = (Block) Block.blockRegistry.getObject(compound.getString("platformBlock"));
    }
}
