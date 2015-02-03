/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlockState(pos).getBlock();

            if (pass == 0)
            {
                BlockPos posBelow = pos.down();
                Block blockBelow = world.getBlockState(posBelow).getBlock();

                if (blockBelow.isNormalCube(world, posBelow) && Blocks.snow_layer.canPlaceBlockAt(world, pos) && block != Blocks.water)
                {
                    if (random.nextInt(5 * 5) == 0)
                    {
                        int b = world.rand.nextInt(6);

                        if (b == 0)
                        {
                            setBlockSafe(world, posBelow, Blocks.netherrack.getDefaultState());
                            setBlockSafe(world, pos, Blocks.fire.getDefaultState());
                        }
                        else if (b == 1)
                        {
                            setBlockSafe(world, pos, Blocks.lit_pumpkin.getDefaultState());
                        }
                        else if (b == 2)
                        {
                            setBlockSafe(world, pos, Blocks.pumpkin.getDefaultState());
                        }
                        else if (b == 3)
                        {
                            setBlockSafe(world, posBelow, Blocks.farmland.getDefaultState());
                            setBlockSafe(world, pos, Blocks.pumpkin_stem.getStateFromMeta(world.rand.nextInt(4) + 4));
                        }
                        else if (b == 4)
                        {
                            setBlockSafe(world, pos, Blocks.cake.getDefaultState());
                        }
                        else if (b == 5)
                        {
                            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(Items.cookie));
                            entityItem.setPickupDelay(20);
                            world.spawnEntityInWorld(entityItem);
                        }
                    }
                }
            }
            else
            {
                if (canSpawnEntity(world, block, pos))
                {
                    lazilySpawnEntity(world, entity, random, "PigZombie", 1.0f / (20 * 20), pos);
                    lazilySpawnEntity(world, entity, random, "Enderman", 1.0f / (20 * 20), pos);
                }
            }
        }
    }
}
