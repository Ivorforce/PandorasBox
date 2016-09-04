/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.PBNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

    public static boolean setBlockToAirSafe(World world, BlockPos pos)
    {
        boolean safeDest = world.getBlockState(pos) == Blocks.AIR || world.getBlockState(pos).getBlockHardness(world, pos) >= 0f;
        return safeDest && world.setBlockToAir(pos);
    }

    public static boolean setBlockSafe(World world, BlockPos pos, IBlockState state)
    {
        boolean safeDest = world.getBlockState(pos) == Blocks.AIR || world.getBlockState(pos).getBlockHardness(world, pos) >= 0f;
        boolean safeSrc = state.getBlock() == Blocks.AIR || state.getBlockHardness(world, pos) >= 0f;

        return safeDest && safeSrc && world.setBlockState(pos, state);
    }

    public static boolean setBlockVarying(World world, BlockPos pos, Block block, int unified)
    {
        return setBlockSafe(world, pos, PandorasBoxHelper.getRandomBlockState(world.rand, block, unified));
    }

    public static EntityPlayer getRandomNearbyPlayer(World world, EntityPandorasBox box)
    {
        return (EntityPlayer) world.findNearestEntityWithinAABB(EntityPlayer.class, box.getEntityBoundingBox().expand(30.0, 30.0, 30.0), box);
    }

    public static EntityPlayer getPlayer(World world, EntityPandorasBox box)
    {
        return getRandomNearbyPlayer(world, box); // We don't know the owner :/
    }

    public static boolean isBlockAnyOf(Block block, Block... blocks)
    {
        for (Block block1 : blocks)
        {
            if (block == block1)
                return true;
        }

        return false;
    }

    public static Entity lazilySpawnEntity(World world, EntityPandorasBox box, Random random, String entityID, float chance, BlockPos pos)
    {
        if (random.nextFloat() < chance)
        {
            Entity entity = PBEffectSpawnEntityIDList.createEntity(world, box, random, entityID, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

            if (entity != null)
            {
                world.spawnEntityInWorld(entity);
                return entity;
            }
        }

        return null;
    }

    public static boolean canSpawnEntity(World world, IBlockState block, BlockPos pos)
    {
        if (world.isRemote)
            return false;

        if (block.getLightOpacity(world, pos) > 0)
            return false;

        if (!world.isBlockNormalCube(pos.down(), false))
            return false;

        return true;
    }

    public boolean canSpawnFlyingEntity(World world, IBlockState block, BlockPos pos)
    {
        if (world.isRemote)
            return false;

        if (block.getLightOpacity(world, pos) > 0 || world.getBlockLightOpacity(pos.down()) > 0 || world.getBlockLightOpacity(pos.down(2)) > 0)
            return false;

        return true;
    }

    public void addPotionEffectDuration(EntityLivingBase entity, PotionEffect potionEffect)
    {
        if (entity.isPotionApplicable(potionEffect))
        {
            boolean addNewEffect = true;

            if (entity.isPotionActive(potionEffect.getPotion()))
            {
                PotionEffect prevEffect = entity.getActivePotionEffect(potionEffect.getPotion());
                if (prevEffect.getAmplifier() == potionEffect.getAmplifier())
                {
                    int duration = prevEffect.getDuration() + potionEffect.getDuration();
                    PotionEffect combined = new PotionEffect(potionEffect.getPotion(), duration, potionEffect.getAmplifier(), potionEffect.getIsAmbient(), potionEffect.doesShowParticles());
                    entity.addPotionEffect(combined);
                }
            }

            if (addNewEffect)
                entity.addPotionEffect(potionEffect);
        }
    }

    public abstract void doTick(EntityPandorasBox entity, Vec3d effectCenter, int ticksAlive);

    public abstract boolean isDone(EntityPandorasBox entity, int ticksAlive);

    public abstract void writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);

    public abstract boolean canGenerateMoreEffectsAfterwards(EntityPandorasBox entity);
}
