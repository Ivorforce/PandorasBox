/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectSpawnItemStacks extends PBEffectSpawnEntities
{
    public ItemStack[] stacks;

    public PBEffectSpawnItemStacks()
    {
    }

    public PBEffectSpawnItemStacks(int time, ItemStack[] stacks)
    {
        super(time, stacks.length);

        setDoesSpawnFromBox(0.1, 0.4, 0.2, 1.0);
        this.stacks = stacks;
    }

    public PBEffectSpawnItemStacks(int time, double range, double shiftY, ItemStack[] stacks)
    {
        super(time, stacks.length);

        setDoesNotSpawnFromBox(range, shiftY);
        this.stacks = stacks;
    }

    @Override
    public Entity spawnEntity(World world, EntityPandorasBox entity, Random random, int number, double x, double y, double z)
    {
        EntityItem entityItem = new EntityItem(world, x, y, z, stacks[number]);
        entityItem.setPickupDelay(10);
        world.spawnEntityInWorld(entityItem);
        return entityItem;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        setNBTStacks("stacks", stacks, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        stacks = getNBTStacks("stacks", compound);
    }
}
