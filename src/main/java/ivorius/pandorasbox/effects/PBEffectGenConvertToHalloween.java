/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToHalloween extends PBEffectGenerate
{
    public PBEffectGenConvertToHalloween()
    {
    }

    public PBEffectGenConvertToHalloween(int time, double range, int unifiedSeed)
    {
        super(time, range, 2, unifiedSeed);
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

                if (blockBelow.isNormalCube(blockBelowState, world, posBelow) && Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos) && block != Blocks.WATER)
                {
                    if (random.nextInt(5 * 5) == 0)
                    {
                        int b = world.rand.nextInt(6);

                        if (b == 0)
                        {
                            setBlockSafe(world, posBelow, Blocks.NETHERRACK.getDefaultState());
                            setBlockSafe(world, pos, Blocks.FIRE.getDefaultState());
                        }
                        else if (b == 1)
                        {
                            setBlockSafe(world, pos, Blocks.LIT_PUMPKIN.getDefaultState());
                        }
                        else if (b == 2)
                        {
                            setBlockSafe(world, pos, Blocks.PUMPKIN.getDefaultState());
                        }
                        else if (b == 3)
                        {
                            setBlockSafe(world, posBelow, Blocks.FARMLAND.getDefaultState());
                            setBlockSafe(world, pos, Blocks.PUMPKIN_STEM.getStateFromMeta(world.rand.nextInt(4) + 4));
                        }
                        else if (b == 4)
                        {
                            setBlockSafe(world, pos, Blocks.CAKE.getDefaultState());
                        }
                        else if (b == 5)
                        {
                            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(Items.COOKIE));
                            entityItem.setPickupDelay(20);
                            world.spawnEntity(entityItem);
                        }
                    }
                }
            }
            else
            {
                if (canSpawnEntity(world, blockState, pos))
                {
                    lazilySpawnEntity(world, entity, random, "PigZombie", 1.0f / (20 * 20), pos);
                    lazilySpawnEntity(world, entity, random, "Enderman", 1.0f / (20 * 20), pos);
                }
            }
        }
    }
}
