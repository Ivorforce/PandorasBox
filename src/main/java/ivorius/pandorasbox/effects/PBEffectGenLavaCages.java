/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.ivtoolkit.blocks.BlockPositions;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.PBNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenLavaCages extends PBEffectGenerate
{
    public Block lavaBlock;
    public Block fillBlock;
    public Block cageBlock;

    public PBEffectGenLavaCages()
    {
    }

    public PBEffectGenLavaCages(int time, double range, int unifiedSeed, Block lavaBlock, Block cageBlock, Block fillBlock)
    {
        super(time, range, 1, unifiedSeed);

        this.lavaBlock = lavaBlock;
        this.cageBlock = cageBlock;
        this.fillBlock = fillBlock;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            if (!world.isBlockNormalCube(pos, false))
            {
                List<EntityPlayer> innerList = world.getEntitiesWithinAABB(EntityPlayer.class, BlockPositions.expandToAABB(pos, (double) 2, (double) 2, (double) 2));

                if (innerList.size() == 0)
                {
                    List<EntityPlayer> outerList = world.getEntitiesWithinAABB(EntityPlayer.class, BlockPositions.expandToAABB(pos, 3.5, 3.5, 3.5));

                    if (outerList.size() > 0)
                    {
                        boolean isCage = true;

                        if (lavaBlock != null)
                        {
                            if (pos.getX() % 3 == 0 && pos.getZ() % 3 == 0 && pos.getY() % 3 == 0)
                            {
                                isCage = false;
                            }
                        }

                        if (isCage)
                        {
                            setBlockVarying(world, pos, cageBlock, unifiedSeed);
                        }
                        else
                        {
                            setBlockVarying(world, pos, lavaBlock, unifiedSeed);
                        }
                    }
                }
                else
                {
                    if (fillBlock != null)
                    {
                        setBlockVarying(world, pos, fillBlock, unifiedSeed);
                    }
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (lavaBlock != null)
            compound.setString("lavaBlock", PBNBTHelper.storeBlockString(lavaBlock));
        if (fillBlock != null)
            compound.setString("fillBlock", PBNBTHelper.storeBlockString(fillBlock));

        compound.setString("cageBlock", PBNBTHelper.storeBlockString(cageBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        lavaBlock = PBNBTHelper.getBlock(compound.getString("lavaBlock"));
        fillBlock = PBNBTHelper.getBlock(compound.getString("fillBlock"));
        cageBlock = PBNBTHelper.getBlock(compound.getString("cageBlock"));
    }
}
