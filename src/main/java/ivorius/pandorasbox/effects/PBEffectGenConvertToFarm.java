/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToFarm extends PBEffectGenerate
{
    private double cropChance;

    public PBEffectGenConvertToFarm()
    {
    }

    public PBEffectGenConvertToFarm(int time, double range, int unifiedSeed, double cropChance)
    {
        super(time, range, 2, unifiedSeed);
        this.cropChance = cropChance;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            IBlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();

            if (pass == 0)
            {
                BlockPos posBelow = pos.down();
                IBlockState blockBelowState = world.getBlockState(posBelow);
                Block blockBelow = blockBelowState.getBlock();

                if (blockBelowState.isNormalCube() && Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos) && block != Blocks.WATER)
                {
                    if (random.nextDouble() < cropChance)
                    {
                        int b = world.rand.nextInt(7);

                        if (b == 0)
                        {
                            setBlockSafe(world, posBelow, Blocks.FARMLAND.getDefaultState());
                            setBlockSafe(world, pos, Blocks.PUMPKIN_STEM.getStateFromMeta(world.rand.nextInt(4) + 4));
                        }
                        else if (b == 1)
                        {
                            setBlockSafe(world, posBelow, Blocks.FARMLAND.getDefaultState());
                            setBlockSafe(world, pos, Blocks.MELON_STEM.getStateFromMeta(world.rand.nextInt(4) + 4));
                        }
                        else if (b == 2)
                        {
                            setBlockSafe(world, posBelow, Blocks.FARMLAND.getDefaultState());
                            setBlockSafe(world, pos, Blocks.WHEAT.getStateFromMeta(world.rand.nextInt(8)));
                        }
                        else if (b == 3)
                        {
                            setBlockSafe(world, posBelow, Blocks.FARMLAND.getDefaultState());
                            setBlockSafe(world, pos, Blocks.CARROTS.getStateFromMeta(world.rand.nextInt(8)));
                        }
                        else if (b == 4)
                        {
                            setBlockSafe(world, posBelow, Blocks.FARMLAND.getDefaultState());
                            setBlockSafe(world, pos, Blocks.POTATOES.getStateFromMeta(world.rand.nextInt(8)));
                        }
                        else if (b == 5)
                        {
                            setBlockSafe(world, pos, Blocks.HAY_BLOCK.getDefaultState());
                        }
                        else if (b == 6)
                        {
                            setBlockSafe(world, posBelow, Blocks.WATER.getDefaultState());
                        }
                    }
                }
            }
            else
            {
                if (canSpawnEntity(world, blockState, pos))
                {
                    lazilySpawnEntity(world, entity, random, "EntityHorse", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Villager", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Pig", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Cow", 1.0f / (50 * 50), pos);
                    lazilySpawnEntity(world, entity, random, "Chicken", 1.0f / (50 * 50), pos);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("cropChance", cropChance);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        cropChance = compound.getDouble("cropChance");
    }
}
