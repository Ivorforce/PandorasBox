/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToDesert extends PBEffectGenerate
{
    public PBEffectGenConvertToDesert()
    {
    }

    public PBEffectGenConvertToDesert(int time, double range, int unifiedSeed)
    {
        super(time, range, 1, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.ice, Blocks.flowing_water, Blocks.water, Blocks.snow, Blocks.snow_layer, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.red_flower, Blocks.yellow_flower, Blocks.vine, Blocks.tallgrass, Blocks.brown_mushroom, Blocks.brown_mushroom_block, Blocks.red_mushroom, Blocks.red_mushroom_block))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (isBlockAnyOf(block, Blocks.soul_sand, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.grass, Blocks.dirt, Blocks.mycelium, Blocks.hardened_clay, Blocks.stained_hardened_clay))
            {
                setBlockSafe(world, x, y, z, Blocks.sand);

                if (world.getBlock(x, y + 1, z).getMaterial() == Material.air)
                {
                    if (!world.isRemote && random.nextInt(20 * 20) == 0)
                    {
                        world.setBlock(x, y + 1, z, Blocks.cactus);
                        world.setBlock(x, y + 2, z, Blocks.cactus);
                        world.setBlock(x, y + 3, z, Blocks.cactus);
                    }
                }
            }
        }
    }
}
