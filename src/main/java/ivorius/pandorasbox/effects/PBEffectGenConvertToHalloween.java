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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlock(x, y, z);

            if (pass == 0)
            {
                Block blockBelow = world.getBlock(x, y - 1, z);

                if (blockBelow.isNormalCube(world, x, y - 1, z) && Blocks.snow_layer.canPlaceBlockAt(world, x, y, z) && block != Blocks.water)
                {
                    if (random.nextInt(5 * 5) == 0)
                    {
                        int b = world.rand.nextInt(6);

                        if (b == 0)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.netherrack);
                            setBlockSafe(world, x, y, z, Blocks.fire);
                        }
                        else if (b == 1)
                        {
                            setBlockSafe(world, x, y, z, Blocks.lit_pumpkin);
                        }
                        else if (b == 2)
                        {
                            setBlockSafe(world, x, y, z, Blocks.pumpkin);
                        }
                        else if (b == 3)
                        {
                            setBlockSafe(world, x, y - 1, z, Blocks.farmland);
                            setBlockAndMetaSafe(world, x, y, z, Blocks.pumpkin_stem, world.rand.nextInt(4) + 4);
                        }
                        else if (b == 4)
                        {
                            setBlockSafe(world, x, y, z, Blocks.cake);
                        }
                        else if (b == 5)
                        {
                            EntityItem entityItem = new EntityItem(world, x + 0.5, y + 0.5f, z + 0.5f, new ItemStack(Items.cookie));
                            entityItem.delayBeforeCanPickup = 20;
                            world.spawnEntityInWorld(entityItem);
                        }
                    }
                }
            }
            else
            {
                if (canSpawnEntity(world, block, x, y, z))
                {
                    lazilySpawnEntity(world, entity, random, "PigZombie", 1.0f / (20 * 20), x, y, z);
                    lazilySpawnEntity(world, entity, random, "Enderman", 1.0f / (20 * 20), x, y, z);
                }
            }
        }
    }
}
