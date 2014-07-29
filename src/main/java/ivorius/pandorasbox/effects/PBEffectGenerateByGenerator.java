/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectGenerateByGenerator extends PBEffectGenerate
{
    public boolean requiresSolidGround;
    public double chancePerBlock;

    public int generatorFlags;

    public PBEffectGenerateByGenerator()
    {
    }

    public PBEffectGenerateByGenerator(int time, double range, int unifiedSeed, boolean requiresSolidGround, double chancePerBlock, int generatorFlags)
    {
        super(time, range, 1, unifiedSeed);

        this.requiresSolidGround = requiresSolidGround;
        this.chancePerBlock = chancePerBlock;
        this.generatorFlags = generatorFlags;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        if (!world.isRemote)
        {
            if (random.nextDouble() < chancePerBlock)
            {
                Block block = world.getBlock(x, y, z);
                Block blockBelow = world.getBlock(x, y - 1, z);

                if (block.getMaterial() == Material.air && (!requiresSolidGround || blockBelow.isNormalCube()))
                {
                    setBlockSafe(world, x, y - 1, z, Blocks.dirt);

                    WorldGenerator generator = getRandomGenerator(getGenerators(), generatorFlags, random);
                    generator.generate(world, random, x, y, z);
                }
            }
        }
    }

    public abstract WorldGenerator[] getGenerators();

    public static WorldGenerator getRandomGenerator(WorldGenerator[] generators, int flags, Random random)
    {
        int totalNumber = 0;

        for (int i = 0; i < generators.length; i++)
        {
            int flag = 1 << i;
            if ((flags & flag) > 0)
            {
                totalNumber++;
            }
        }

        int chosenGen = random.nextInt(totalNumber);
        for (int i = 0; i < generators.length; i++)
        {
            int flag = 1 << i;
            if ((flags & flag) > 0)
            {
                if (chosenGen == 0)
                {
                    return generators[i];
                }

                chosenGen--;
            }
        }

        return null;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("requiresSolidGround", requiresSolidGround);
        compound.setDouble("chancePerBlock", chancePerBlock);
        compound.setInteger("generatorFlags", generatorFlags);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        requiresSolidGround = compound.getBoolean("requiresSolidGround");
        chancePerBlock = compound.getDouble("chancePerBlock");
        generatorFlags = compound.getInteger("generatorFlags");
    }
}
