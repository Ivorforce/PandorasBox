/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToRainbowCloth extends PBEffectGenerate
{
    public int[] woolMetas;
    public double ringSize;

    public PBEffectGenConvertToRainbowCloth()
    {
    }

    public PBEffectGenConvertToRainbowCloth(int time, double range, int unifiedSeed, int[] woolMetas, double ringSize)
    {
        super(time, range, 1, unifiedSeed);
        this.woolMetas = woolMetas;
        this.ringSize = ringSize;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (world.isBlockNormalCube(pos, false))
            {
                if (world.getBlockState(pos.up()) == Blocks.AIR)
                {
                    double dist = MathHelper.sqrt(effectCenter.squareDistanceTo(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)));
                    setBlockSafe(world, pos, Blocks.WOOL.getStateFromMeta(woolMetas[MathHelper.floor(dist / ringSize) % woolMetas.length]));
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setIntArray("woolMetas", woolMetas);
        compound.setDouble("ringSize", ringSize);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        woolMetas = compound.getIntArray("woolMetas");
        ringSize = compound.getDouble("ringSize");
    }
}
