/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.ice, Blocks.flowing_water, Blocks.water, Blocks.snow, Blocks.snow_layer, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.red_flower, Blocks.yellow_flower, Blocks.vine, Blocks.tallgrass, Blocks.brown_mushroom, Blocks.brown_mushroom_block, Blocks.red_mushroom, Blocks.red_mushroom_block))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.soul_sand, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.grass, Blocks.dirt, Blocks.mycelium, Blocks.hardened_clay, Blocks.stained_hardened_clay))
            {
                setBlockSafe(world, pos, Blocks.sand.getDefaultState());

                if (world.getBlockState(pos.up()).getBlock().getMaterial() == Material.air)
                {
                    if (!world.isRemote && random.nextInt(20 * 20) == 0)
                    {
                        setBlockSafe(world, pos.up(1), Blocks.cactus.getDefaultState());
                        setBlockSafe(world, pos.up(2), Blocks.cactus.getDefaultState());
                        setBlockSafe(world, pos.up(3), Blocks.cactus.getDefaultState());
                    }
                }
            }
        }
    }
}
