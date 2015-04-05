/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToLifeless extends PBEffectGenerate
{
    public PBEffectGenConvertToLifeless()
    {
    }

    public PBEffectGenConvertToLifeless(int time, double range, int unifiedSeed)
    {
        super(time, range, 1, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.flowing_water, Blocks.ice, Blocks.water, Blocks.lava, Blocks.flowing_lava, Blocks.snow, Blocks.snow_layer))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.vine, Blocks.brown_mushroom_block, Blocks.red_mushroom_block, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.fire))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.red_flower, Blocks.yellow_flower, Blocks.tallgrass))
            {
                setBlockSafe(world, pos, Blocks.deadbush.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.mycelium, Blocks.grass, Blocks.wool, Blocks.cake))
            {
                setBlockSafe(world, pos, Blocks.dirt.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.netherrack, Blocks.stained_hardened_clay, Blocks.end_stone))
            {
                setBlockSafe(world, pos, Blocks.stone.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.soul_sand))
            {
                setBlockSafe(world, pos, Blocks.sand.getDefaultState());
            }
        }
    }
}
