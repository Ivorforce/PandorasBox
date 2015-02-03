/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.IvBlockPosHelper;
import ivorius.pandorasbox.utils.IvNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            if (world.isBlockNormalCube(pos, false))
            {
                boolean setPlatform = false;

                if (platformBlock != null)
                {
                    List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, IvBlockPosHelper.expandBlockPos(pos, 2.5, 2.5, 2.5));

                    if (entityList.size() > 0)
                    {
                        setPlatform = true;
                    }
                }

                if (setPlatform)
                {
                    setBlockVarying(world, pos, platformBlock, unifiedSeed);
                }
                else
                {
                    setBlockVarying(world, pos, block, unifiedSeed);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("block", IvNBTHelper.storeBlockString(block));

        if (platformBlock != null)
            compound.setString("platformBlock", IvNBTHelper.storeBlockString(platformBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        block = IvNBTHelper.getBlock(compound.getString("block"));
        platformBlock = IvNBTHelper.getBlock(compound.getString("platformBlock"));
    }
}
