/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectGenerateByStructure extends PBEffectNormal
{
    public Structure[] structures;

    public PBEffectGenerateByStructure()
    {
    }

    public PBEffectGenerateByStructure(int maxTicksAlive)
    {
        super(maxTicksAlive);
        structures = new Structure[0];
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Random random, float newRatio, float prevRatio)
    {
        for (Structure structure : structures)
        {
            float newStructureRatio = getStructureRatio(newRatio, structure);
            float prevStructureRatio = getStructureRatio(prevRatio, structure);

            int baseX = MathHelper.floor_double(entity.posX);
            int baseY = MathHelper.floor_double(entity.posY);
            int baseZ = MathHelper.floor_double(entity.posZ);

            if (newStructureRatio > prevStructureRatio)
            {
                generateStructure(world, entity, random, structure, baseX, baseY, baseZ, newStructureRatio, prevStructureRatio);
            }
        }
    }

    private float getStructureRatio(float ratio, Structure structure)
    {
        return MathHelper.clamp_float((ratio - structure.structureStart) / structure.structureLength, 0.0f, 1.0f);
    }

    public abstract void generateStructure(World world, EntityPandorasBox entity, Random random, Structure structure, int x, int y, int z, float newRatio, float prevRatio);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList structureTagList = new NBTTagList();
        for (Structure structure : structures)
        {
            NBTTagCompound structureCompound = new NBTTagCompound();
            structure.writeToNBT(structureCompound);
            structureTagList.appendTag(structureCompound);
        }
        compound.setTag("structures", structureTagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagList structureTagList = compound.getTagList("structures", Constants.NBT.TAG_COMPOUND);
        structures = new Structure[structureTagList.tagCount()];
        for (int i = 0; i < structures.length; i++)
        {
            structures[i] = createStructure(structureTagList.getCompoundTagAt(i));
        }
    }

    public abstract Structure createStructure();

    public Structure createStructure(NBTTagCompound compound)
    {
        Structure structure = createStructure();
        structure.readFromNBT(compound);
        return structure;
    }

    public static void applyRandomProperties(Structure structure, double range, Random random)
    {
        structure.structureLength = random.nextFloat() * 0.8f + 0.1f;
        structure.structureStart = random.nextFloat() * (1.0f - structure.structureLength);

        structure.x = MathHelper.floor_double((random.nextDouble() - random.nextDouble()) * range);
        structure.y = MathHelper.floor_double((random.nextDouble() - random.nextDouble()) * range);
        structure.z = MathHelper.floor_double((random.nextDouble() - random.nextDouble()) * range);

        structure.unifiedSeed = PandorasBoxHelper.getRandomUnifiedSeed(random);
    }

    public static class Structure
    {
        public float structureStart;
        public float structureLength;

        public int x;
        public int y;
        public int z;

        public int unifiedSeed;

        public Structure()
        {

        }

        public Structure(float structureStart, float structureLength, int x, int y, int z, int unifiedSeed)
        {
            this.structureStart = structureStart;
            this.structureLength = structureLength;
            this.x = x;
            this.y = y;
            this.z = z;
            this.unifiedSeed = unifiedSeed;
        }

        public void writeToNBT(NBTTagCompound compound)
        {
            compound.setFloat("structureStart", structureStart);
            compound.setFloat("structureLength", structureLength);

            compound.setInteger("x", x);
            compound.setInteger("y", y);
            compound.setInteger("z", z);
            compound.setInteger("unifiedSeed", unifiedSeed);
        }

        public void readFromNBT(NBTTagCompound compound)
        {
            structureStart = compound.getFloat("structureStart");
            structureLength = compound.getFloat("structureLength");

            x = compound.getInteger("x");
            y = compound.getInteger("y");
            z = compound.getInteger("z");
            unifiedSeed = compound.getInteger("unifiedSeed");
        }
    }
}
