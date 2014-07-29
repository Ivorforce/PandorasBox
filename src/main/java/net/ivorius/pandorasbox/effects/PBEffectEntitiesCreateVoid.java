/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesCreateVoid extends PBEffectEntityBased
{
    public PBEffectEntitiesCreateVoid()
    {
    }

    public PBEffectEntitiesCreateVoid(int maxTicksAlive, double range)
    {
        super(maxTicksAlive, range);
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        if (!world.isRemote)
        {
            int baseY = MathHelper.floor_double(entity.posY);
            int baseX = MathHelper.floor_double(entity.posX);
            int baseZ = MathHelper.floor_double(entity.posZ);

            for (int x = -1; x <= 1; x++)
            {
                for (int y = -8; y <= 2; y++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        setBlockSafe(world, baseX + x, baseY + y, baseZ + z, Blocks.air);
                    }
                }
            }
        }
    }
}
