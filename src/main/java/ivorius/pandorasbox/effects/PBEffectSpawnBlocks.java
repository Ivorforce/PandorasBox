/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectSpawnBlocks extends PBEffectSpawnEntities
{
    public Block[] blocks;

    public PBEffectSpawnBlocks()
    {
    }

    public PBEffectSpawnBlocks(int time, Block[] blocks)
    {
        super(time, blocks.length);

        setDoesSpawnFromBox(0.1, 0.4, 0.2, 1.0);
        this.blocks = blocks;
    }

    public PBEffectSpawnBlocks(int time, double range, double shiftY, Block[] blocks)
    {
        super(time, blocks.length);

        setDoesNotSpawnFromBox(range, shiftY);
        this.blocks = blocks;
    }

    @Override
    public Entity createEntity(World world, EntityPandorasBox entity, Random random, int number, double x, double y, double z)
    {
        Block block = blocks[number];
        EntityFallingBlock entityFallingBlock = new EntityFallingBlock(world, x, y, z, block);
        entityFallingBlock.field_145812_b = 1;

        return entityFallingBlock;
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
