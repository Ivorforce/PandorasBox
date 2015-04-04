/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.PBNBTHelper;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenShapes extends PBEffectGenerateByStructure
{
    public PBEffectGenShapes()
    {
    }

    public PBEffectGenShapes(int maxTicksAlive)
    {
        super(maxTicksAlive);
    }

    public void setRandomShapes(Random random, Collection<WeightedBlock> blocks, double range, double minSize, double maxSize, int number, int shape)
    {
        structures = new Structure[number];

        for (int i = 0; i < structures.length; i++)
        {
            StructureShape randomShape = createStructure();
            applyRandomProperties(randomShape, range, random);
            randomShape.shapeType = shape < 0 ? random.nextInt(4) : shape;
            randomShape.size = minSize + random.nextDouble() * (maxSize - minSize);
            randomShape.blocks = PandorasBoxHelper.getRandomBlockList(random, blocks);

            structures[i] = randomShape;
        }
    }

    public void setShapes(Random random, Block[] blockSelection, double range, double minSize, double maxSize, int number, int shape, int unifiedSeed)
    {
        structures = new Structure[number];

        for (int i = 0; i < structures.length; i++)
        {
            StructureShape randomShape = createStructure();
            applyRandomProperties(randomShape, range, random);
            randomShape.shapeType = shape < 0 ? random.nextInt(4) : shape;
            randomShape.size = minSize + random.nextDouble() * (maxSize - minSize);
            randomShape.blocks = blockSelection.clone();
            randomShape.unifiedSeed = unifiedSeed;

            structures[i] = randomShape;
        }
    }

    @Override
    public void generateStructure(World world, EntityPandorasBox entity, Random random, Structure structure, BlockPos pos, float newRatio, float prevRatio)
    {
        if (!world.isRemote)
        {
            StructureShape structureShape = (StructureShape) structure;
            double prevSize = structureShape.size * prevRatio;
            double newSize = structureShape.size * newRatio;

            if (structureShape.shapeType == 0)
            {
                int requiredRange = MathHelper.floor_double(newSize);

                for (int xPlus = -requiredRange; xPlus <= requiredRange; xPlus++)
                {
                    for (int yPlus = -requiredRange; yPlus <= requiredRange; yPlus++)
                    {
                        for (int zPlus = -requiredRange; zPlus <= requiredRange; zPlus++)
                        {
                            double dist = MathHelper.sqrt_double(xPlus * xPlus + yPlus * yPlus + zPlus * zPlus);

                            if (dist <= newSize)
                            {
                                if (dist > prevSize)
                                {
                                    generateOnBlock(world, entity, random, structureShape, pos.add(structure.x + xPlus, structure.y + yPlus, structure.z + zPlus));
                                }
                                else
                                {
                                    zPlus = -zPlus; // We can skip all blocks in between
                                }
                            }
                        }
                    }
                }
            }
            else if (structureShape.shapeType == 1)
            {
                int requiredRange = MathHelper.floor_double(newSize);

                for (int xPlus = -requiredRange; xPlus <= requiredRange; xPlus++)
                {
                    for (int yPlus = -requiredRange; yPlus <= requiredRange; yPlus++)
                    {
                        for (int zPlus = -requiredRange; zPlus <= requiredRange; zPlus++)
                        {
                            double xDist = Math.abs(xPlus);
                            double yDist = Math.abs(yPlus);
                            double zDist = Math.abs(zPlus);

                            if (xDist <= newSize && yDist <= newSize && zDist <= newSize)
                            {
                                if (xDist > prevSize || yDist > prevSize || zDist > prevSize)
                                {
                                    generateOnBlock(world, entity, random, structureShape, pos.add(structure.x + xPlus, structure.y + yPlus, structure.z + zPlus));
                                }
                                else
                                {
                                    zPlus = -zPlus; // We can skip all blocks in between
                                }
                            }
                        }
                    }
                }
            }
            else if (structureShape.shapeType == 2 || structureShape.shapeType == 3)
            {
                int requiredRange = MathHelper.floor_double(newSize);
                int totalHeight = MathHelper.floor_double(structureShape.size);

                for (int yPlus = -requiredRange; yPlus <= requiredRange; yPlus++)
                {
                    int yDist = Math.abs(yPlus);

                    if (yDist <= newSize)
                    {
                        if (yDist > prevSize)
                        {
                            int levelSize = structureShape.shapeType == 2 ? (totalHeight - yDist) : (yDist + 1);

                            for (int xPlus = -levelSize; xPlus <= levelSize; xPlus++)
                            {
                                for (int zPlus = -levelSize; zPlus <= levelSize; zPlus++)
                                {
                                    generateOnBlock(world, entity, random, structureShape, pos.add(structure.x + xPlus, structure.y + yPlus, structure.z + zPlus));
                                }
                            }
                        }
                        else
                        {
                            yPlus = -yPlus; // We can skip all blocks in between
                        }
                    }
                }
            }
        }
    }

    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, StructureShape structure, BlockPos pos)
    {
        Block block = structure.blocks[random.nextInt(structure.blocks.length)];
        setBlockVarying(world, pos, block, structure.unifiedSeed);
    }

    @Override
    public StructureShape createStructure()
    {
        return new StructureShape();
    }

    public static class StructureShape extends Structure
    {
        public Block[] blocks;

        public int shapeType;
        public double size;

        public StructureShape()
        {
        }

        @Override
        public void writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            PBNBTHelper.writeNBTBlocks("blocks", blocks, compound);

            compound.setInteger("shapeType", shapeType);
            compound.setDouble("size", size);
        }

        @Override
        public void readFromNBT(NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            blocks = PBNBTHelper.readNBTBlocks("blocks", compound);

            shapeType = compound.getInteger("shapeType");
            size = compound.getDouble("size");
        }
    }
}
