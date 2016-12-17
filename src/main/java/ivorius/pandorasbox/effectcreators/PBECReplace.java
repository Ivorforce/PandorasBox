/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenReplace;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.ZValue;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECReplace implements PBEffectCreator
{
    public DValue range;

    public Block[] srcBlocks;
    public Collection<WeightedBlock> destBlocks;

    public ZValue takeRandomNearbyBlocks;

    public PBECReplace(DValue range, Block[] srcBlocks, Collection<WeightedBlock> destBlocks, ZValue takeRandomNearbyBlocks)
    {
        this.range = range;
        this.srcBlocks = srcBlocks;
        this.destBlocks = destBlocks;
        this.takeRandomNearbyBlocks = takeRandomNearbyBlocks;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = MathHelper.floor((random.nextDouble() * 7.0 + 3.0) * range);

        int baseX = MathHelper.floor(x);
        int baseY = MathHelper.floor(y);
        int baseZ = MathHelper.floor(z);
        boolean takeRandomNearbyBlocks = this.takeRandomNearbyBlocks.getValue(random);

        Block[] srcSelection = new Block[0];

        if (takeRandomNearbyBlocks)
        {
            List<WeightedBlock> nearbyBlocks = new ArrayList<WeightedBlock>();
            for (int xP = -5; xP <= 5; xP++)
            {
                for (int yP = -5; yP <= 5; yP++)
                {
                    for (int zP = -5; zP <= 5; zP++)
                    {
                        IBlockState block = world.getBlockState(new BlockPos(baseX + xP, baseY + yP, baseZ + zP));

                        if (block.getMaterial() != Material.AIR)
                            nearbyBlocks.add(new WeightedBlock(100, block.getBlock()));
//                        else // dud
                    }
                }
            }

            if (nearbyBlocks.size() > 0)
            {
                srcSelection = PandorasBoxHelper.getRandomBlockList(random, nearbyBlocks);
            }
        }
        else
        {
            srcSelection = srcBlocks.clone();
        }

        Block[] destSelection = PandorasBoxHelper.getRandomBlockList(random, destBlocks);

        PBEffectGenReplace genReplace = new PBEffectGenReplace(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), destSelection, srcSelection);
        return genReplace;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
