/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectGenerate2D extends PBEffectRangeBased
{
    public int unifiedSeed;

    public PBEffectGenerate2D()
    {
    }

    public PBEffectGenerate2D(int time, double range, int passes, int unifiedSeed)
    {
        super(time, range, passes);

        this.unifiedSeed = unifiedSeed;
    }

    @Override
    public void generateInRange(World world, EntityPandorasBox entity, Random random, Vec3d effectCenter, double prevRange, double newRange, int pass)
    {
        int requiredRange = MathHelper.floor(newRange);

        int baseX = MathHelper.floor(effectCenter.x);
        int baseY = MathHelper.floor(effectCenter.y);
        int baseZ = MathHelper.floor(effectCenter.z);

        for (int x = -requiredRange; x <= requiredRange; x++)
        {
            for (int z = -requiredRange; z <= requiredRange; z++)
            {
                double dist = MathHelper.sqrt(x * x + z * z);

                if (dist <= newRange)
                {
                    if (dist > prevRange)
                    {
                        generateOnSurface(world, entity, effectCenter, random, new BlockPos(baseX + x, baseY, baseZ + z), dist, pass);
                    }
                    else
                    {
                        z = -z; // We can skip all blocks in between
                    }
                }
            }
        }
    }

    public abstract void generateOnSurface(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, BlockPos pos, double distance, int pass);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("unifiedSeed", unifiedSeed);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        unifiedSeed = compound.getInteger("unifiedSeed");
    }
}
