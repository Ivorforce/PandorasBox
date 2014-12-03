/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffect
{
    public String getEffectID()
    {
        return PBEffectRegistry.getEffectID(this);
    }

    public static String[] getNBTStrings(String id, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = compound.getTagList(id, Constants.NBT.TAG_STRING);
        String[] strings = new String[nbtTagList.tagCount()];

        for (int i = 0; i < strings.length; i++)
        {
            strings[i] = nbtTagList.getStringTagAt(i);
        }

        return strings;
    }

    public static void setNBTStrings(String id, String[] strings, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (String s : strings)
        {
            nbtTagList.appendTag(new NBTTagString(s));
        }

        compound.setTag(id, nbtTagList);
    }

    public static String[][] getNBTStrings2D(String id, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = compound.getTagList(id, Constants.NBT.TAG_COMPOUND);
        String[][] strings = new String[nbtTagList.tagCount()][];

        for (int i = 0; i < strings.length; i++)
        {
            strings[i] = getNBTStrings("Strings", nbtTagList.getCompoundTagAt(i));
        }

        return strings;
    }

    public static void setNBTStrings2D(String id, String[][] strings, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (String[] s : strings)
        {
            NBTTagCompound compound1 = new NBTTagCompound();
            setNBTStrings("Strings", s, compound1);
            nbtTagList.appendTag(compound1);
        }

        compound.setTag(id, nbtTagList);
    }

    public static ItemStack[] getNBTStacks(String id, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = compound.getTagList(id, Constants.NBT.TAG_COMPOUND);
        ItemStack[] itemStacks = new ItemStack[nbtTagList.tagCount()];

        for (int i = 0; i < itemStacks.length; i++)
        {
            itemStacks[i] = ItemStack.loadItemStackFromNBT(nbtTagList.getCompoundTagAt(i));
        }

        return itemStacks;
    }

    public static void setNBTStacks(String id, ItemStack[] stacks, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (ItemStack stack : stacks)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);
            nbtTagList.appendTag(tagCompound);
        }

        compound.setTag(id, nbtTagList);
    }

    public static Block[] getNBTBlocks(String id, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = compound.getTagList(id, Constants.NBT.TAG_STRING);
        Block[] blocks = new Block[nbtTagList.tagCount()];

        for (int i = 0; i < blocks.length; i++)
        {
            blocks[i] = (Block) Block.blockRegistry.getObject(nbtTagList.getStringTagAt(i));
        }

        return blocks;
    }

    public static void setNBTBlocks(String id, Block[] blocks, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (Block b : blocks)
        {
            nbtTagList.appendTag(new NBTTagString(Block.blockRegistry.getNameForObject(b)));
        }

        compound.setTag(id, nbtTagList);
    }

    public static long[] getNBTLongs(String id, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = compound.getTagList(id, Constants.NBT.TAG_STRING);
        long[] longs = new long[nbtTagList.tagCount()];

        for (int i = 0; i < longs.length; i++)
        {
            int[] parts = nbtTagList.func_150306_c(i);
            longs[i] = (long) parts[0] + ((long) parts[1] << 32);
        }

        return longs;
    }

    public static void setNBTLongs(String id, long[] longs, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (long l : longs)
        {
            int lower = (int) l;
            int upper = (int) (l >>> 32);
            nbtTagList.appendTag(new NBTTagIntArray(new int[]{(int) l, (int) (l >>> 32)}));
        }

        compound.setTag(id, nbtTagList);
    }

    public static PotionEffect[] getNBTPotions(String id, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = compound.getTagList(id, Constants.NBT.TAG_STRING);
        PotionEffect[] potions = new PotionEffect[nbtTagList.tagCount()];

        for (int i = 0; i < potions.length; i++)
        {
            potions[i] = PotionEffect.readCustomPotionEffectFromNBT(nbtTagList.getCompoundTagAt(i));
        }

        return potions;
    }

    public static void setNBTPotions(String id, PotionEffect[] potions, NBTTagCompound compound)
    {
        NBTTagList nbtTagList = new NBTTagList();

        for (PotionEffect p : potions)
        {
            nbtTagList.appendTag(p.writeCustomPotionEffectToNBT(new NBTTagCompound()));
        }

        compound.setTag(id, nbtTagList);
    }

    public static boolean setBlockSafe(World world, int x, int y, int z, Block block)
    {
        boolean safeDest = world.getBlock(x, y, z) == Blocks.air || world.getBlock(x, y, z).getBlockHardness(world, x, y, z) >= 0f;
        boolean safeSrc = block == Blocks.air || block.getBlockHardness(world, x, y, z) >= 0f;

        if (safeDest && safeSrc)
        {
            world.setBlock(x, y, z, block, 0, 3);
            return true;
        }

        else
        {
            return false;
        }
    }

    public static boolean setBlockAndMetaSafe(World world, int x, int y, int z, Block id, int meta)
    {
        boolean safeDest = world.getBlock(x, y, z) == Blocks.air || world.getBlock(x, y, z).getBlockHardness(world, x, y, z) >= 0f;
        boolean safeSrc = id == Blocks.air || id.getBlockHardness(world, x, y, z) >= 0f;

        if (safeDest && safeSrc)
        {
            world.setBlock(x, y, z, id, meta, 3);
            return true;
        }

        else
        {
            return false;
        }
    }

    public static boolean setBlockVarying(World world, int x, int y, int z, Block block, int unified)
    {
        return setBlockAndMetaSafe(world, x, y, z, block, PandorasBoxHelper.getRandomBlockMetadata(world.rand, block, unified));
    }

    public static EntityPlayer getRandomNearbyPlayer(World world, EntityPandorasBox box)
    {
        return (EntityPlayer) world.findNearestEntityWithinAABB(EntityPlayer.class, box.boundingBox.expand(30.0, 30.0, 30.0), box);
    }

    public static EntityPlayer getPlayer(World world, EntityPandorasBox box)
    {
        return getRandomNearbyPlayer(world, box); // We don't know the owner :/
    }

    public boolean isBlockAnyOf(Block block, Block... blocks)
    {
        for (Block block1 : blocks)
        {
            if (block == block1)
            {
                return true;
            }
        }

        return false;
    }

    public Entity lazilySpawnEntity(World world, EntityPandorasBox box, Random random, String entityID, float chance, int x, int y, int z)
    {
        if (random.nextFloat() < chance)
        {
            Entity entity = PBEffectSpawnEntityIDList.createEntity(world, box, random, entityID, x, y, z);

            if (entity != null)
            {
                world.spawnEntityInWorld(entity);
                return entity;
            }
        }

        return null;
    }

    public boolean canSpawnEntity(World world, Block block, int x, int y, int z)
    {
        if (world.isRemote)
        {
            return false;
        }

        if (block.getLightOpacity(world, x, y, z) > 0)
        {
            return false;
        }

        if (!world.isBlockNormalCubeDefault(x, y - 1, z, false))
        {
            return false;
        }

        return true;
    }

    public boolean canSpawnFlyingEntity(World world, Block block, int x, int y, int z)
    {
        if (world.isRemote)
        {
            return false;
        }

        if (block.getLightOpacity(world, x, y, z) > 0 || world.getBlockLightOpacity(x, y - 1, z) > 0 || world.getBlockLightOpacity(x, y - 2, z) > 0)
        {
            return false;
        }

        return true;
    }

    public void addPotionEffectDuration(EntityLivingBase entity, PotionEffect potionEffect)
    {
        if (entity.isPotionApplicable(potionEffect))
        {
            boolean addNewEffect = true;

            if (entity.isPotionActive(potionEffect.getPotionID()))
            {
                PotionEffect prevEffect = entity.getActivePotionEffect(Potion.potionTypes[potionEffect.getPotionID()]);
                if (prevEffect.getAmplifier() == potionEffect.getAmplifier())
                {
                    int duration = prevEffect.getDuration() + potionEffect.getDuration();
                    PotionEffect combined = new PotionEffect(potionEffect.getPotionID(), duration, potionEffect.getAmplifier(), potionEffect.getIsAmbient());
                    entity.addPotionEffect(combined);
                }
            }

            if (addNewEffect)
            {
                entity.addPotionEffect(potionEffect);
            }
        }
    }

    public abstract void doTick(EntityPandorasBox entity, Vec3 effectCenter, int ticksAlive);

    public abstract boolean isDone(EntityPandorasBox entity, int ticksAlive);

    public abstract void writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);

    public abstract boolean canGenerateMoreEffectsAfterwards(EntityPandorasBox entity);
}
