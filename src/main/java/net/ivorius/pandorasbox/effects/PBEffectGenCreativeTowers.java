/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenCreativeTowers extends PBEffectGenerateByStructure
{
    public PBEffectGenCreativeTowers()
    {
    }

    public PBEffectGenCreativeTowers(int maxTicksAlive)
    {
        super(maxTicksAlive);
    }

    public void createRandomStructures(Random random, int number, double range, Collection<WeightedBlock> blocks)
    {
        this.structures = new Structure[number];
        for (int i = 0; i < number; i++)
        {
            structures[i] = createStructure();
            applyRandomProperties(structures[i], range, random);
            ((StructureCreativeTower) structures[i]).blocks = PandorasBoxHelper.getRandomBlockList(random, blocks);
        }
    }

    @Override
    public void generateStructure(World world, EntityPandorasBox entity, Random random, Structure structure, int x, int y, int z, float newRatio, float prevRatio)
    {
        if (!world.isRemote)
        {
            StructureCreativeTower structureCreativeTower = (StructureCreativeTower) structure;

            int towerHeight = world.getHeight();
            int newY = MathHelper.floor_float(towerHeight * newRatio);
            int prevY = MathHelper.floor_float(towerHeight * prevRatio);

            for (int towerY = prevY; towerY < newY; towerY++)
            {
                Block block = structureCreativeTower.blocks[random.nextInt(structureCreativeTower.blocks.length)];

                setBlockVarying(world, x + structure.x, towerY, z + structure.z, block, structure.unifiedSeed);
            }
        }
    }

    @Override
    public StructureCreativeTower createStructure()
    {
        return new StructureCreativeTower();
    }

    public static class StructureCreativeTower extends Structure
    {
        public Block[] blocks;

        public StructureCreativeTower()
        {
        }

        @Override
        public void writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            setNBTBlocks("blocks", blocks, compound);
        }

        @Override
        public void readFromNBT(NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            blocks = getNBTBlocks("blocks", compound);
        }
    }
}
