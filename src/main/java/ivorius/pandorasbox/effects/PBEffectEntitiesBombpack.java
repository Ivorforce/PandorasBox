/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesBombpack extends PBEffectEntityBased
{
    public PBEffectEntitiesBombpack()
    {
    }

    public PBEffectEntitiesBombpack(int maxTicksAlive, double range)
    {
        super(maxTicksAlive, range);
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        if (!world.isRemote)
        {
            Random itemRandom = new Random(entity.getEntityId());
            double expectedBomb = itemRandom.nextDouble();
            if (newRatio >= expectedBomb && prevRatio < expectedBomb)
            {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, entity.posX, entity.posY, entity.posZ, null);
//                entitytntprimed.fuse = 60 + random.nextInt(160); // Use normal fuse for correct visual effect

                world.spawnEntity(entitytntprimed);
                entitytntprimed.startRiding(entity, true);
            }
        }
    }
}
