/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        if (!world.isRemote)
        {
            if (!world.isBlockNormalCubeDefault(x, y, z, false))
            {
                List<EntityPlayer> innerList = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x - 2.0, y - 2.0, z - 2.0, x + 3.0, y + 3.0, z + 3.0));

                if (innerList.size() == 0)
                {
                    List<EntityPlayer> outerList = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x - 3.5, y - 3.5, z - 3.5, x + 4.5, y + 4.5, z + 4.5));

                    if (outerList.size() > 0)
                    {
                        boolean isCage = true;

                        if (lavaBlock != null)
                        {
                            if (x % 3 == 0 && z % 3 == 0 && y % 3 == 0)
                            {
                                isCage = false;
                            }
                        }

                        if (isCage)
                        {
                            setBlockVarying(world, x, y, z, cageBlock, unifiedSeed);
                        }
                        else
                        {
                            setBlockVarying(world, x, y, z, lavaBlock, unifiedSeed);
                        }
                    }
                }
                else
                {
                    if (fillBlock != null)
                    {
                        setBlockVarying(world, x, y, z, fillBlock, unifiedSeed);
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
        {
            compound.setString("lavaBlock", Block.blockRegistry.getNameForObject(lavaBlock));
        }
        if (fillBlock != null)
        {
            compound.setString("fillBlock", Block.blockRegistry.getNameForObject(fillBlock));
        }

        compound.setString("cageBlock", Block.blockRegistry.getNameForObject(cageBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        lavaBlock = (Block) Block.blockRegistry.getObject(compound.getString("lavaBlock"));
        fillBlock = (Block) Block.blockRegistry.getObject(compound.getString("fillBlock"));
        cageBlock = (Block) Block.blockRegistry.getObject(compound.getString("cageBlock"));
    }
}
